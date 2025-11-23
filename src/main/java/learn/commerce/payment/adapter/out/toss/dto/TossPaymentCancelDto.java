package learn.commerce.payment.adapter.out.toss.dto;

public record TossPaymentCancelDto(
        String cancelReason,
        int cancelAmount
) {
}