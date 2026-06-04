# 주문 트랜잭션 · 재고 동시성 보강

> 2024년 KG ITBANK 팀 프로젝트(편의점)의 결제·주문 흐름에 트랜잭션 처리가 없던 것을
> 직접 발견해, 이후 학습 과정에서 정합성과 재고 동시성을 보강한 기록입니다.
> (원본 2024 제출본에는 없던 개선이며, 트랜잭션/동시성 학습을 위해 직접 추가했습니다.)

---

## 1. 배경 — 발견한 문제

결제 성공 시 `PaymentService.setOrderInfo()` 가 아래를 순차 실행했습니다.

1. `insertOrders` — 주문 헤더
2. 장바구니 항목마다: `insertOrderdetail` → `updateInventory`(재고 차감) → `insertStorage`(픽업물품)
3. 결제 영수증 이메일 발송

여기에 네 가지 문제가 있었습니다.

| # | 문제 | 영향 |
|---|---|---|
| 1 | `root-context.xml` 에 `transactionManager` 도 `<tx:annotation-driven>` 도 없음 | `@Transactional` 을 붙여도 **무효** |
| 2 | 위 쓰기들이 하나의 트랜잭션이 아님 | 중간 실패 시 "주문 헤더는 생겼는데 재고는 안 빠진" **깨진 상태** 잔존 |
| 3 | `update inventory set cnt = cnt - ?` 에 재고 확인 없음 | 동시 주문 시 **초과판매 · 재고 음수** |
| 4 | 이메일 발송이 같은 메서드에 혼재 | 트랜잭션이 외부 I/O 까지 물고 있음 |

추가로 컨트롤러가 예외를 `printStackTrace()` 로 삼켜, 실패해도 성공 화면이 보였습니다.

---

## 2. 설계

1. **트랜잭션 인프라**: `root-context.xml` 에 `DataSourceTransactionManager` + `<tx:annotation-driven>` 추가.
   `spring-jdbc` 가 이미 의존성에 있어 **새 라이브러리 추가 0개**.
2. **트랜잭션 경계 분리**: 주문 확정 로직을 전용 `OrderService.placeOrder()`(`@Transactional`)로 추출.
   `PaymentService` 가 이 **다른 빈**을 호출하므로 Spring AOP 프록시가 정상 적용됩니다
   (같은 빈 내부 호출 시 프록시가 우회되는 self-invocation 함정 회피). 이메일은 커밋 후 별도 발송.
3. **재고 정합성 — 원자적 조건부 UPDATE**:
   ```sql
   update inventory set cnt = cnt - #{cnt}
    where store_idx = #{store_idx}
      and product_idx = #{product_idx}
      and cnt >= #{cnt}
   ```
   차감된 행이 0이면 재고 부족이므로 `OutOfStockException`(RuntimeException)을 던져
   트랜잭션을 롤백합니다. 별도 락 없이 **DB 행 단위 원자성**만으로 초과판매를 막습니다.

---

## 3. 동시성 전략을 왜 "조건부 UPDATE" 로 골랐나

| 전략 | 방식 | 트레이드오프 |
|---|---|---|
| **조건부 UPDATE (채택)** | `UPDATE ... WHERE cnt >= 수량`, 영향 행 0이면 실패 처리 | 락을 명시하지 않고 행 원자성만으로 안전, 코드 최소, 읽기 무블로킹, 재시도 불필요 |
| 비관적 락 | `SELECT ... FOR UPDATE` 로 잠근 뒤 확인→차감 | 명시적이지만 행 잠금 대기 비용, 코드 증가 |
| 낙관적 락 | `version` 컬럼 비교, 충돌 시 재시도 | 경합이 낮을 때 유리하나 스키마 변경 + 재시도 로직 필요 |

단발적 재고 차감에는 조건부 UPDATE 가 가장 단순하고 정확합니다.

---

## 4. 검증 — TDD, 독립 H2 하니스

`tx-harness/` 는 레거시 war 전체를 빌드하지 않고, 트랜잭션 로직 클래스 4개
(`OrderService`·`PaymentRepository`·`CartDTO`·`OutOfStockException`)만 떼어
H2 인메모리(Oracle 호환 모드)로 검증합니다.

| 테스트 | 시나리오 | 기대 |
|---|---|---|
| **T1 롤백** | 재고 5개에 10개 주문 | `OutOfStockException` + 주문 헤더·상세 전부 롤백, 재고 5 유지 |
| **T2 동시성** | 재고 1개에 동시 주문 10건 | 정확히 1건 성공·9건 재고부족, 재고 0(음수 불가), 주문 1건 |
| **B1 쿠폰 롤백** | 이미 사용된 쿠폰으로 주문 | `CouponAlreadyUsedException` + 주문·재고 전체 롤백 |
| **B2 쿠폰 동시성** | 같은 쿠폰으로 동시 주문 5건 | 정확히 1건만 쿠폰 사용·주문 성공 |
| **C 멱등성** | 같은 orderId 로 2회 호출 | 주문 1건·재고 1회만 차감 (중복 무시) |

실행:
```bash
# JDK 11+ 필요
mvn -f tx-harness/pom.xml test
```

---

## 5. 한계와 확장

- 조건부 UPDATE 는 **단일 DB** 기준 정합성입니다. 분산·다중 인스턴스에서도 최종 방어선은
  DB(제약 · 원자적 UPDATE)이며, 애플리케이션 캐시 재고를 둔다면 분산 락(ShedLock/Redis)이나
  DB 유니크/체크 제약을 병행해야 합니다.
- **쿠폰 중복 사용 방지**(B), **결제 멱등성**(C)도 같은 "원자적 제약" 원칙으로 구현했습니다.
  - 쿠폰: `update usercoupon set useddate=current_timestamp where idx=? and useddate is null` 의
    영향 행이 0이면 `CouponAlreadyUsedException` → 롤백 (재고와 동일 패턴).
  - 멱등성: `orderId` 존재 확인 + `orders` PK 로 같은 주문의 중복 반영을 차단.
  - 쿠폰 사용·장바구니 비우기를 주문 트랜잭션 **안**으로 옮겨, 주문 실패 시 쿠폰·장바구니도 함께 롤백되도록 했습니다.

---

## 변경 파일

- `src/main/webapp/WEB-INF/spring/root-context.xml` — 트랜잭션 매니저 + `tx:annotation-driven`
- `src/main/java/com/itbank/service/OrderService.java` — `@Transactional placeOrder()` (신규)
- `src/main/java/com/itbank/exception/OutOfStockException.java` — 재고 부족 예외 (신규)
- `src/main/java/com/itbank/repository/PaymentRepository.java` — `updateInventory` 조건부 차감 + 영향 행 반환
- `src/main/java/com/itbank/service/PaymentService.java` — `placeOrder` 위임, 이메일 분리
- `src/main/java/com/itbank/controller/PaymentController.java` — 실패 시 안내(예외 삼킴 제거)
- `tx-harness/` — 독립 트랜잭션 검증 모듈
