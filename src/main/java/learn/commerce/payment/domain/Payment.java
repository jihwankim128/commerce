package learn.commerce.payment.domain;

import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.common.utils.UuidConverter;
import learn.commerce.payment.domain.vo.Ledgers;
import learn.commerce.payment.domain.vo.PaymentId;
import learn.commerce.payment.domain.vo.PaymentMethod;
import learn.commerce.payment.domain.vo.PaymentStatus;
import lombok.Getter;

@Getter
public class Payment {
    private final PaymentId paymentId;
    private final UUID orderId;
    private final PaymentMethod method;
    private final String paymentKey;
    private final Ledgers ledgers;
    private PaymentStatus status;

    private Payment(PaymentId paymentId, UUID orderId, Ledgers ledgers, PaymentMethod method, String paymentKey) {
        if (paymentKey == null || paymentKey.isBlank()) {
            throw new IllegalArgumentException("PaymentKey는 필수 정보입니다");
        }
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.method = method;
        this.paymentKey = paymentKey;
        this.status = PaymentStatus.DONE;
        this.ledgers = ledgers;
    }

    public static Payment approve(String orderId, Money amount, PaymentMethod method, String paymentKey) {
        return new Payment(
                PaymentId.generate(),
                UuidConverter.fromString(orderId),
                Ledgers.recordApproval(amount),
                method,
                paymentKey);
    }

    public void cancel(Money cancelAmount, String reason) {
        if (this.status == PaymentStatus.CANCELED) {
            throw new IllegalArgumentException("이미 취소가 완료된 결제입니다");
        }

        ledgers.recordCancellation(cancelAmount, reason);
        if (ledgers.isFullyCancelled()) {
            this.status = PaymentStatus.CANCELED;
        }
    }
}