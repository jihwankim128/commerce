package learn.commerce.payment.domain.vo;

import java.util.UUID;

public record PaymentId(UUID value) {

    public PaymentId {
        if (value == null) {
            throw new IllegalArgumentException("결제 ID는 필수입니다");
        }
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}