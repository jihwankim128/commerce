package learn.commerce.payment.adapter.out.toss.dto;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;

public record TossPaymentApprovalDto(
        String paymentKey,
        String orderId,
        Integer amount
) {
    public static TossPaymentApprovalDto from(PaymentApproval approval) {
        return new TossPaymentApprovalDto(
                approval.paymentKey(),
                approval.orderId(),
                approval.amount()
        );
    }
}