package com.itbank.service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itbank.exception.CouponAlreadyUsedException;
import com.itbank.exception.OutOfStockException;
import com.itbank.model.CartDTO;
import com.itbank.repository.PaymentRepository;

/**
 * 주문 확정(주문 헤더/상세 생성 + 재고 차감 + 픽업물품 등록)을 하나의 트랜잭션으로 묶는 서비스.
 *
 * - {@code @Transactional} 로 네 단계 쓰기를 원자화한다: 한 단계라도 실패하면 전부 롤백된다.
 * - 재고 차감은 {@code update ... where cnt >= 요청수량} 원자적 조건부 UPDATE 로 수행하고,
 *   차감된 행이 0이면 재고가 부족한 것이므로 {@link OutOfStockException} 을 던져 롤백시킨다.
 *   별도 락 없이 DB 행 단위 원자성만으로 동시 주문의 초과판매(재고 음수)를 막는다.
 * - 이메일 등 외부 I/O 는 이 트랜잭션 밖(커밋 후)에서 호출자가 처리한다.
 */
@Service
public class OrderService {

    @Autowired
    private PaymentRepository pr;

    @Transactional
    public void placeOrder(String userid, String paymentKey, String orderId, int amount,
                           int store_idx, String pickupCode,
                           List<CartDTO> cart, int[] storageCnt,
                           int couponIdx, int[] cartIdx) {

        // 0) 멱등성: 이미 처리된 주문이면 아무 작업도 하지 않는다 (재시도/중복 요청 대비)
        if (pr.countOrder(orderId) > 0) {
            return;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("userid", userid);
        map.put("paymentKey", paymentKey);
        map.put("orderId", orderId);
        map.put("amount", amount);
        map.put("store_idx", store_idx);
        map.put("pickupCode", pickupCode);

        // 1) 주문 헤더
        pr.insertOrders(map);

        for (int i = 0; i < cart.size(); i++) {
            int product_idx = cart.get(i).getProduct_idx();
            int cnt = cart.get(i).getCnt() + cart.get(i).getEventCnt();
            int price = cart.get(i).getPrice() * cart.get(i).getCnt();
            int rescount = storageCnt[i];
            String orderdetail_idx = UUID.randomUUID().toString().substring(0, 8);

            map.put("product_idx", product_idx);
            map.put("cnt", cnt);
            map.put("price", price);
            map.put("orderdetail_idx", orderdetail_idx);

            // 2) 주문 상세
            pr.insertOrderdetail(map);

            // 3) 재고 차감 (조건부 원자 UPDATE). 차감된 행이 0이면 재고 부족 → 예외 → 전체 롤백
            int affected = pr.updateInventory(map);
            if (affected == 0) {
                throw new OutOfStockException(product_idx, cnt);
            }

            // 4) 픽업물품(나만의 냉장고) 등록
            if (rescount > 0) {
                map.put("rescount", rescount);
                pr.insertStorage(map);
            }
        }

        // 5) 쿠폰 원자적 사용 (아직 사용되지 않은 경우에만). 이미 사용됐으면 예외 → 전체 롤백
        if (couponIdx != 0) {
            int used = pr.useCoupon(couponIdx);
            if (used == 0) {
                throw new CouponAlreadyUsedException(couponIdx);
            }
        }

        // 6) 결제된 상품을 장바구니에서 제거 (같은 트랜잭션 안에서 처리해 정합성 보장)
        if (cartIdx != null) {
            for (int idx : cartIdx) {
                pr.removeCart(idx);
            }
        }
    }
}
