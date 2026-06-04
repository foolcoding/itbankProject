package com.itbank.exception;

/**
 * 이미 사용된 쿠폰을 다시 사용하려 할 때 던지는 예외.
 *
 * RuntimeException(unchecked)이므로 @Transactional 기본 롤백 규칙에 따라
 * 주문 전체가 롤백된다. (재고 부족과 동일하게 "조건부 UPDATE 영향 행 0" 으로 감지)
 */
public class CouponAlreadyUsedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CouponAlreadyUsedException(int userCouponIdx) {
        super("이미 사용된 쿠폰입니다: usercoupon idx=" + userCouponIdx);
    }
}
