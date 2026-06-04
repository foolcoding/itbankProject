package com.itbank.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itbank.exception.CouponAlreadyUsedException;
import com.itbank.exception.OutOfStockException;
import com.itbank.model.CartDTO;
import com.itbank.service.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-context.xml")
public class OrderServiceTxTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    private static final int[] NO_CART = new int[] {};

    @Before
    public void setUp() {
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DROP ALL OBJECTS");
        jdbc.execute("CREATE TABLE orders (idx VARCHAR(50) PRIMARY KEY, paymentKey VARCHAR(100), userid VARCHAR(50), store_idx INT, pickupCode VARCHAR(20), amount INT)");
        jdbc.execute("CREATE TABLE orderdetail (idx VARCHAR(50) PRIMARY KEY, orders_idx VARCHAR(50), product_idx INT, cnt INT, price INT)");
        jdbc.execute("CREATE TABLE inventory (store_idx INT, product_idx INT, cnt INT, PRIMARY KEY (store_idx, product_idx))");
        jdbc.execute("CREATE TABLE storageitem (idx INT, orderdetail_idx VARCHAR(50), userid VARCHAR(50), totalcount INT, rescount INT)");
        jdbc.execute("CREATE TABLE usercoupon (idx INT PRIMARY KEY, userid VARCHAR(50), coupon_idx INT, useddate TIMESTAMP)");
        jdbc.execute("CREATE TABLE cart (idx INT PRIMARY KEY, userid VARCHAR(50), product_idx INT, cnt INT)");
    }

    private List<CartDTO> oneLineCart(int productIdx, int cnt, int price, int storeIdx) {
        CartDTO c = new CartDTO();
        c.setProduct_idx(productIdx);
        c.setCnt(cnt);
        c.setEventCnt(0);
        c.setPrice(price);
        c.setStore_idx(storeIdx);
        List<CartDTO> list = new ArrayList<>();
        list.add(c);
        return list;
    }

    private int count(String table) {
        return jdbc.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
    }

    private int inventoryCnt(int storeIdx, int productIdx) {
        return jdbc.queryForObject(
                "SELECT cnt FROM inventory WHERE store_idx=? AND product_idx=?",
                Integer.class, storeIdx, productIdx);
    }

    private boolean couponUsed(int userCouponIdx) {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM usercoupon WHERE idx=? AND useddate IS NOT NULL",
                Integer.class, userCouponIdx) > 0;
    }

    /** T1: 재고 부족 시 이미 들어간 주문 헤더/상세까지 전부 롤백. */
    @Test
    public void 재고가_부족하면_주문_전체가_롤백된다() {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 5)");
        List<CartDTO> cart = oneLineCart(10, 10, 1000, 1); // 주문 10 > 재고 5

        try {
            orderService.placeOrder("user1", "pk1", "order1", 10000, 1, "PICK0001", cart, new int[] { 0 }, 0, NO_CART);
            fail("재고가 부족하면 OutOfStockException 이 발생해야 한다");
        } catch (OutOfStockException expected) {
        }

        assertEquals("주문 헤더가 롤백되어야 한다", 0, count("orders"));
        assertEquals("재고는 차감되지 않아야 한다", 5, inventoryCnt(1, 10));
    }

    /** T2: 재고 1개에 동시 주문 10건이 몰려도 정확히 1건만 성공. */
    @Test
    public void 동시_주문이_재고보다_많아도_초과판매되지_않는다() throws Exception {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 1)");
        runConcurrent(10, i -> orderService.placeOrder(
                "user", "pk", "order-" + i, 1000, 1, "P" + i, oneLineCart(10, 1, 1000, 1), new int[] { 0 }, 0, NO_CART));

        assertEquals("재고 1개이므로 정확히 1건만 성공", 1, count("orders"));
        assertEquals("재고는 음수가 되지 않고 0", 0, inventoryCnt(1, 10));
    }

    /** B-1: 이미 사용된 쿠폰으로 주문하면 예외가 나고 주문 전체가 롤백된다. */
    @Test
    public void 이미_사용된_쿠폰이면_주문이_롤백된다() {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 5)");
        jdbc.update("INSERT INTO usercoupon (idx, userid, coupon_idx, useddate) VALUES (100, 'u', 1, CURRENT_TIMESTAMP)"); // 이미 사용됨

        try {
            orderService.placeOrder("u", "pk", "ob1", 1000, 1, "PB1", oneLineCart(10, 1, 1000, 1), new int[] { 0 }, 100, NO_CART);
            fail("이미 사용된 쿠폰이면 CouponAlreadyUsedException 이 발생해야 한다");
        } catch (CouponAlreadyUsedException expected) {
        }

        assertEquals("주문이 롤백되어야 한다", 0, count("orders"));
        assertEquals("재고도 차감되지 않아야 한다", 5, inventoryCnt(1, 10));
    }

    /** B-2: 같은 쿠폰으로 동시 주문이 몰려도 정확히 1건만 쿠폰을 사용한다. */
    @Test
    public void 동시에_같은_쿠폰을_써도_한_번만_사용된다() throws Exception {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 100)"); // 재고는 충분
        jdbc.update("INSERT INTO usercoupon (idx, userid, coupon_idx, useddate) VALUES (100, 'u', 1, NULL)"); // 미사용

        runConcurrent(5, i -> orderService.placeOrder(
                "u", "pk", "oc-" + i, 1000, 1, "P" + i, oneLineCart(10, 1, 1000, 1), new int[] { 0 }, 100, NO_CART));

        assertEquals("쿠폰을 쓴 주문은 정확히 1건만 성공", 1, count("orders"));
        assertEquals("쿠폰은 사용 처리되어야 한다", true, couponUsed(100));
    }

    /** C: 같은 orderId 로 두 번 호출해도 주문/재고가 중복 반영되지 않는다(멱등성). */
    @Test
    public void 같은_주문ID로_재시도해도_중복_처리되지_않는다() {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 5)");

        orderService.placeOrder("u", "pk", "dup", 1000, 1, "PDUP", oneLineCart(10, 1, 1000, 1), new int[] { 0 }, 0, NO_CART);
        orderService.placeOrder("u", "pk", "dup", 1000, 1, "PDUP", oneLineCart(10, 1, 1000, 1), new int[] { 0 }, 0, NO_CART); // 재시도

        assertEquals("주문은 1건만 존재해야 한다", 1, count("orders"));
        assertEquals("재고는 한 번만 차감되어 4여야 한다", 4, inventoryCnt(1, 10));
    }

    // ---- 동시 실행 헬퍼 ----
    private interface OrderTask {
        void run(int i) throws Exception;
    }

    private void runConcurrent(int threads, OrderTask task) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        final CountDownLatch ready = new CountDownLatch(threads);
        final CountDownLatch start = new CountDownLatch(1);
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final int idx = i;
            futures.add(pool.submit(() -> {
                ready.countDown();
                try {
                    start.await();
                    task.run(idx);
                } catch (RuntimeException ignore) {
                    // 재고/쿠폰 부족 등으로 실패한 건은 롤백됨 (정상 경로)
                } catch (Exception ignore) {
                }
            }));
        }
        ready.await();
        start.countDown();
        for (Future<?> f : futures) {
            f.get();
        }
        pool.shutdown();
    }
}
