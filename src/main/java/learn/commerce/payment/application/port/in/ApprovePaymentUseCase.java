package learn.commerce.payment.application.port.in;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;

public interface ApprovePaymentUseCase {

    void approvePayment(PaymentApproval command);
}
