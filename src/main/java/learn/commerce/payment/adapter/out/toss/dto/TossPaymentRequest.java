package learn.commerce.payment.adapter.out.toss.dto;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;

public record TossPaymentRequest(
        String paymentKey,
        String orderId,
        Integer amount
) {
    public static TossPaymentRequest from(PaymentApproval approval) {
        return new TossPaymentRequest(
                approval.paymentKey(),
                approval.orderId(),
                approval.amount()
        );
    }
}