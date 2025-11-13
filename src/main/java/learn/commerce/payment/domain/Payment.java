package learn.commerce.payment.domain;

import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.common.utils.UuidConverter;
import learn.commerce.payment.domain.vo.PaymentId;
import learn.commerce.payment.domain.vo.PaymentMethod;
import learn.commerce.payment.domain.vo.PaymentStatus;
import lombok.Getter;

@Getter
public class Payment {
    private final PaymentId paymentId;
    private final UUID orderId;
    private final Money amount;
    private final PaymentMethod method;
    private final String paymentKey;
    private final PaymentStatus status;

    private Payment(PaymentId paymentId, UUID orderId, Money amount, PaymentMethod method, String paymentKey) {
        if (amount == null) {
            throw new IllegalArgumentException("가격 정보는 필수입니다");
        }
        if (paymentKey == null || paymentKey.isBlank()) {
            throw new IllegalArgumentException("PaymentKey는 필수 정보입니다");
        }
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.paymentKey = paymentKey;
        this.status = PaymentStatus.DONE;
    }

    public static Payment approve(String orderId, Money amount, PaymentMethod method, String paymentKey) {
        return new Payment(PaymentId.generate(), UuidConverter.fromString(orderId), amount, method, paymentKey);
    }
}