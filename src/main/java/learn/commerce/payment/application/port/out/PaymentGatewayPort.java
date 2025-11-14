package learn.commerce.payment.application.port.out;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.dto.result.PaymentApprovalResult;

public interface PaymentGatewayPort {
    PaymentApprovalResult callApproval(PaymentApproval approval);

    boolean validateApproval(String paymentStatus);
}
