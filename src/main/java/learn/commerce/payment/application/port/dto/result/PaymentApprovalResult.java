package learn.commerce.payment.application.port.dto.result;

public record PaymentApprovalResult(
        String paymentKey,
        String orderId,
        String method,
        Integer totalAmount,
        String status,
        String approvedAt
) {
}