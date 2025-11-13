package learn.commerce.payment.domain.vo;

public enum PaymentMethod {
    CARD;

    public static PaymentMethod of(String method) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.name().equalsIgnoreCase(method)) {
                return paymentMethod;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 결제 수단입니다.");
    }
}