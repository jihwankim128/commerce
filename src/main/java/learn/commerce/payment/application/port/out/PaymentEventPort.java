package learn.commerce.payment.application.port.out;

import learn.commerce.payment.domain.Payment;

public interface PaymentEventPort {

    void publishApproval(Payment payment);
}
