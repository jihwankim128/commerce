package learn.acceptance.config;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.dto.result.PaymentApprovalResult;
import learn.commerce.payment.application.port.out.PaymentGatewayPort;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Primary;

@TestComponent
@Primary
public class FakePaymentGateway implements PaymentGatewayPort {
    @Override
    public PaymentApprovalResult callApproval(PaymentApproval approval) {
        return new PaymentApprovalResult(
                approval.paymentKey(),
                approval.orderId(),
                "카드",
                approval.amount(),
                "DONE",
                "NOW"
        );
    }

    @Override
    public boolean validateApproval(String paymentStatus) {
        return true;
    }

    @Override
    public void cancelPayment(String paymentKey, int cancelAmount, String cancelReason) {

    }
}
