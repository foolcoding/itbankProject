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

    @Before
    public void setUp() {
        jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("DROP ALL OBJECTS");
        jdbc.execute("CREATE TABLE orders (idx VARCHAR(50) PRIMARY KEY, paymentKey VARCHAR(100), userid VARCHAR(50), store_idx INT, pickupCode VARCHAR(20), amount INT)");
        jdbc.execute("CREATE TABLE orderdetail (idx VARCHAR(50) PRIMARY KEY, orders_idx VARCHAR(50), product_idx INT, cnt INT, price INT)");
        jdbc.execute("CREATE TABLE inventory (store_idx INT, product_idx INT, cnt INT, PRIMARY KEY (store_idx, product_idx))");
        jdbc.execute("CREATE TABLE storageitem (idx INT, orderdetail_idx VARCHAR(50), userid VARCHAR(50), totalcount INT, rescount INT)");
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

    /** T1: 재고보다 많이 주문하면 이미 들어간 주문 헤더/상세까지 전부 롤백되어야 한다. */
    @Test
    public void 재고가_부족하면_주문_전체가_롤백된다() {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 5)");
        List<CartDTO> cart = oneLineCart(10, 10, 1000, 1); // 주문 10개 > 재고 5개

        try {
            orderService.placeOrder("user1", "pk1", "order1", 10000, 1, "PICK0001", cart, new int[] { 0 });
            fail("재고가 부족하면 OutOfStockException 이 발생해야 한다");
        } catch (OutOfStockException expected) {
            // 기대된 예외
        }

        assertEquals("주문 헤더가 롤백되어야 한다", 0, count("orders"));
        assertEquals("주문 상세가 롤백되어야 한다", 0, count("orderdetail"));
        assertEquals("재고는 차감되지 않아야 한다", 5, inventoryCnt(1, 10));
    }

    /** T2: 재고 1개에 동시 주문 10건이 몰려도 정확히 1건만 성공하고 재고는 음수가 되지 않아야 한다. */
    @Test
    public void 동시_주문이_재고보다_많아도_초과판매되지_않는다() throws Exception {
        jdbc.update("INSERT INTO inventory (store_idx, product_idx, cnt) VALUES (1, 10, 1)"); // 재고 1개

        final int threads = 10;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        final CountDownLatch ready = new CountDownLatch(threads);
        final CountDownLatch start = new CountDownLatch(1);
        final AtomicInteger success = new AtomicInteger();
        final AtomicInteger outOfStock = new AtomicInteger();

        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            final String orderId = "order-" + i;
            futures.add(pool.submit(() -> {
                ready.countDown();
                try {
                    start.await(); // 모든 스레드를 동시에 출발시킨다
                    orderService.placeOrder("user", "pk", orderId, 1000, 1, "P" + orderId,
                            oneLineCart(10, 1, 1000, 1), new int[] { 0 });
                    success.incrementAndGet();
                } catch (OutOfStockException e) {
                    outOfStock.incrementAndGet();
                } catch (Exception ignore) {
                    // 그 외 예외는 성공/재고부족 어느 쪽도 아니므로 단언에서 걸러진다
                }
            }));
        }

        ready.await();
        start.countDown();
        for (Future<?> f : futures) {
            f.get();
        }
        pool.shutdown();

        assertEquals("재고 1개이므로 정확히 1건만 성공해야 한다", 1, success.get());
        assertEquals("나머지 9건은 재고부족으로 실패해야 한다", 9, outOfStock.get());
        assertEquals("재고는 음수가 되지 않고 0이어야 한다", 0, inventoryCnt(1, 10));
        assertEquals("주문은 정확히 1건만 생성되어야 한다", 1, count("orders"));
    }
}
