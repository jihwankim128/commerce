package learn.commerce.payment.adapter.out.event.dto;

import learn.commerce.payment.domain.Payment;

public record ApprovalEvent(String paymentId, String orderId) {
    public static ApprovalEvent from(Payment payment) {
        return new ApprovalEvent(
                payment.getPaymentId().toString(),
                payment.getOrderId().toString()
        );
    }
}
