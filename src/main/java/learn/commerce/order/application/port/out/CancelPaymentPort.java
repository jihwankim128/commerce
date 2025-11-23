package learn.commerce.order.application.port.out;

import learn.commerce.common.domain.Money;

public interface CancelPaymentPort {
    void cancelPayment(String paymentKey, Money cancelAmount, String reason);
}