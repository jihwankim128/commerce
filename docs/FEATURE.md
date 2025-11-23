# 프로젝트 개요

![image.png](attachment:9a50d0ca-d422-4978-b993-d0f052e76396:image.png)

토스페이먼츠 API를 활용한 주문 및 결제 시스템을 **DDD(Domain-Driven-Design)**으로 모델링한다.
**Event Storming**으로 도메인을 분석하고, **Clean Architecture** 기반 **ATDD** 방식으로 개발을 진행한다.

1. 사용자는 상품을 주문하고 결제를 통해 상품을 구매한다.
2. 사용자는 주문을 취소하고 결제가 취소된다.

## 핵심 목표

- [x]  Spring REST Docs를 통한 API 문서화
- [x]  Event Storming 기반 도메인 설계
- [x]  Domain-Driven Design (DDD)
- Bounded Context 분리 (Order / Payment)
- Aggregate 설계
- Domain Event를 통한 Policy 구현
- [x]  클린 아키텍처 (Clean Architecture)
- [x]  ATDD (Acceptance Test-Driven Development)

---

# Event Storming

> **목표: Event Storming 결과로 ATDD를 진행하는 것**
>

프리코스 미션 중 Domain 개념을 학습하며 DDD(Domain Driven Design)에 대해 얼핏 알게 됐다.
그 중 EventStorming은 DDD를 실행하기 위한 방식으로 도메인 설계를 통해 시나리오를 식별할 수 있다.

짧은 기간 내에 많은 기능을 구현하기 보다는 하나에 대해 깊게 학습하는 것이 목표이므로 최소 기능으로 설계한다.

![img.png](img/bounded-context.png)

## Event & Command 도출

- 시간 순으로 발생할 수 있는 이벤트와 명령을 도출한다. (Domain 로직)

## External & Aggregate 도출

- 외부 시스템과 Aggregate를 도출한다. (Application 로직)
    - 주문은 주문 도메인(Aggregate Root)로 처리
    - 결제는 결제 Domain(Aggregate Root)로 처리

## Policy & Actor 도출

- 각 이벤트에 대한 정책과 이벤트 호출자를 도출한다.
    - 정책 상 연계되는 이벤트는 Actor를 시스템이라고 정의했다.

## **Bounded Context & ETC 도출**

- 각 이벤트에 대한 경계를 도출하고 기타 세부 스티커를 작성한다.
    - 현재 사이즈에서는 Commerce Context로 통합할 수 있지만, CleanArchitecture를 경험하기 위해 Order Context와 Payment Context로 분리했다.
        - Context간 동기적인 처리가 필수인 경우 화살표로 표시한다.
    - HotSpot(Red)으로 추가적인 의견을 작성해도 된다.
        - 주문 미완료 건 지우기, 구매 확정 처리하기, 판매자 정산, …
        - 우선은 최소 기능으로 진행한다. 위 기능들은 당장 없어도 주문 시스템에 큰 영향이 없다.
    - 작업이 끝나고 조회(Web Hook URL 등)가 필수적인 경우 Read(Green)을 작성해도 된다.

## **Event Flow**

```
Phase 1: 주문 생성
구매자 → 주문 생성 (Command) → 주문이 생성됨 (Event)

Phase 2: 결제 승인
클라이언트 → 결제 확인 (Command) → 결제가 승인됨 (Event)
        → [Policy] 주문 완료 처리 → 주문이 완료됨 (Event)

Phase 3: 사용자 주문 취소
구매자 → 주문 취소 (Command) → 주문이 취소됨 (Event)
     → [Policy] 결제 취소 처리 → 결제가 취소됨 (Event)
```

---

# 기능 요구사항

## **Phase 1: 주문 생성**

### **사용자 스토리**

```
구매자는 상품을 선택하여 주문 생성 요청을 한다.
```

### **인수 조건 (Acceptance Criteria)**

- 구매자는 주문자 정보(이름, 전화번호)를 입력한다
- 구매자는 1개 이상의 상품을 선택한다
- 각 상품은 ID, 이름, 가격, 수량을 포함한다
- 시스템은 총 주문 금액을 자동으로 계산한다
- 시스템은 고유한 주문 ID(UUID)를 발급한다
- 주문 생성 후 주문 상태는 "ORDER_COMPLETED"이다

### **도메인 규칙**

```
Order Aggregate 불변식:
- 주문 항목 ≥ 1개
- 총 금액 = Σ(각 항목의 단가 × 수량)
- 주문자 이름: 필수
- 전화번호: 010으로 시작하는 11자리 (하이픈 없이)
- 상품 가격: 0원 이상
- 주문 수량: 1개 이상
```

---

## **Phase 2: 결제 승인**

### **사용자 스토리**

```
클라이언트는 사용자가 생성한 주문 정보로 토스페이먼츠로부터 결제 요청 후,
서버로 결제 승인 요청을 통해 결제 승인 결과를 확인한다.
```

### **인수 조건 (Acceptance Criteria)**

- 클라이언트는 결제 확인 요청을 보낸다
- 시스템은 주문 상태가 "ORDER_COMPLETED"인지 확인한다
- 시스템은 주문 금액과 결제 금액이 일치하는지 검증한다
- 토스페이먼츠 API를 통해 결제를 승인한다
- 결제 승인 시 Payment Aggregate를 생성한다
- 결제 원장(PaymentLedger)에 거래 내역을 기록한다
- `PaymentApproved` 이벤트가 발행된다
    - 주문 상태가 "PAYMENT_FULLFILL"로 변경된다.

### **도메인 규칙**

```
Payment Aggregate 불변식:
- 카드 결제만 허용 
- 결제 Key: 필수
- 승인 내역 기록

Order Aggregate 상태 전이:
- 주문 상태 = PAYMENT_FULL_FILL (결제 완료 상태)
```

### **이벤트**

- 결제가 승인되었다 → 주문 완료

---

## **Phase 3: 사용자 주문 취소**

### **사용자 스토리**

```
구매자는 결제 완료된 주문에 대해 취소할 경우, 결제가 취소되고 환불을 받게 된다.
```

### **인수 조건 (Acceptance Criteria)**

- 구매자는 주문 취소를 요청한다
- 시스템은 주문이 취소 가능한 상태인지 검증한다
    - 이미 취소된 경우 재취소 할 수 없다.
- 시스템은 취소 사유를 입력받는다
- 전체 취소 또는 부분 취소를 선택할 수 있다
    - 부분 취소 시 특정 주문 항목을 선택한다
- [Policy] 결제 취소가 요청된다
- 토스페이먼츠 API를 통해 결제를 취소한다
- 결제 원장에 취소 내역을 기록한다
- 전체 주문 취소 후 주문 상태는 “ORDER_CANCELED”가 된다.
- 부분 주문 취소 후 주문 상태는 “PARITIAL_CANCELED”가 된다.

### **도메인 규칙**

```
Order Aggregate:
- 취소 가능 상태: PAYMENT_FULL_FILL
- 전체 취소 상태: ORDER_CANCELED
- 부분 취소 상태: PARITIAL_CANCELED
- 부분 취소 시 항목별 취소 가능

Payment Aggregate:
- 취소 금액 ≤ 잔여 금액 (balanceAmount)
  - 이미 취소된 금액 계산
- 취소 사유: 필수
- 전체 결제 내역이 취소된 후 상태: CANCELED

```

---

# 설계 제약사항

## **Domain Layer**

- 순수 Java 객체만 사용
- JPA 어노테이션 금지
- 프레임워크 독립적
- VO는 `record`로 작성
- Aggregate Root는 일관성 경계 보장

## **의존성 방향**

```
Adapter → Application → Domain
(외부)      (유스케이스)   (핵심)
```

## **검증 책임**

- Request DTO: @NotNull 정도만 (null 방어)
- VO (Value Object): 비즈니스 규칙 검증
- Aggregate: 불변식 보장

### **Aggregate 설계**

- ID로만 참조 (직접 참조 금지)
- 트랜잭션 경계 = Aggregate 경계
- Application Event 및 Adapter Call로 Aggregate 간 통신
    - DDD는 모델링에만 사용하므로 Domain Event 미사용