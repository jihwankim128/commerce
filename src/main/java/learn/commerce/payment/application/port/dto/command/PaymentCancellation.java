package learn.commerce.payment.application.port.dto.command;

import learn.commerce.common.domain.Money;
import learn.commerce.common.utils.UuidConverter;
import learn.commerce.payment.domain.vo.PaymentId;

public record PaymentCancellation(
        String paymentKey,
        int cancelAmount,
        String cancelReason
) {

    public PaymentId toPaymentId() {
        return new PaymentId(UuidConverter.fromString(paymentKey));
    }

    public Money toCancelAmount() {
        return new Money(cancelAmount);
    }
}
