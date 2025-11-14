package learn.commerce.payment.application.port.dto.command;

import learn.commerce.common.domain.Money;
import learn.commerce.payment.domain.Payment;
import learn.commerce.payment.domain.vo.PaymentMethod;

public record PaymentApproval(
        String paymentKey,
        String orderId,
        int amount
) {

    public Payment toDomain(String method) {
        return Payment.approve(orderId, new Money(amount), PaymentMethod.of(method), paymentKey);
    }
}
