package learn.commerce.payment.domain;

import learn.commerce.payment.domain.vo.PaymentId;

public interface PaymentRepository {

    Payment save(Payment payment);

    Payment getByIdWithThrow(PaymentId paymentId);
}
