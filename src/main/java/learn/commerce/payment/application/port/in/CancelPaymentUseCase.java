package learn.commerce.payment.application.port.in;

import learn.commerce.payment.application.port.dto.command.PaymentCancellation;

public interface CancelPaymentUseCase {

    void cancel(PaymentCancellation command);
}
