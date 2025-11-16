package learn.commerce.payment.domain.event;

import java.time.LocalDateTime;
import learn.commerce.payment.domain.Payment;

public record PaymentApprovedEvent(
        String paymentId,
        String orderId,
        String paymentKey,
        int amount,
        LocalDateTime occurredAt
) {
    public static PaymentApprovedEvent from(Payment payment) {
        return new PaymentApprovedEvent(
                payment.getPaymentId().toString(),
                payment.getOrderId().toString(),
                payment.getPaymentKey(),
                payment.getAmount().amount(),
                LocalDateTime.now()
        );
    }
}