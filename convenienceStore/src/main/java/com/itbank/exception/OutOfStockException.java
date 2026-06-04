package com.itbank.exception;

/**
 * 재고가 부족해 주문을 확정할 수 없을 때 던지는 예외.
 *
 * RuntimeException(unchecked)을 상속하므로 Spring @Transactional 의 기본 롤백 규칙에 따라
 * 별도 rollbackFor 설정 없이도 트랜잭션이 롤백된다.
 */
public class OutOfStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OutOfStockException(int productIdx, int requested) {
        super("재고 부족: product_idx=" + productIdx + ", 요청수량=" + requested);
    }
}
