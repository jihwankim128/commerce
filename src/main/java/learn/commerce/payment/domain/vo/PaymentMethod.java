package learn.commerce.payment.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentMethod {
    CARD("카드");

    private final String value;

    public static PaymentMethod of(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.value.equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }

        throw new IllegalArgumentException(method + "는 아직 지원하지 않는 결제 수단입니다.");
    }
}