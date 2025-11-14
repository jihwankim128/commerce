package learn.commerce.payment.application.service;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.dto.result.PaymentApprovalResult;
import learn.commerce.payment.application.port.in.ApprovePaymentUseCase;
import learn.commerce.payment.application.port.out.OrderPaymentPort;
import learn.commerce.payment.application.port.out.PaymentGatewayPort;
import learn.commerce.payment.domain.Payment;
import learn.commerce.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService implements ApprovePaymentUseCase {

    private final PaymentGatewayPort paymentGatewayPort;
    private final OrderPaymentPort orderPaymentPort;
    private final PaymentRepository paymentRepository;

    @Override
    public void approvePayment(PaymentApproval command) {
        validatePayable(command.orderId(), command.amount());

        PaymentApprovalResult response = paymentGatewayPort.callApproval(command);
        validateApprovalStatus(response.status());

        Payment payment = command.toDomain(response.method());
        paymentRepository.save(payment);
        orderPaymentPort.completePayment(command.orderId(), command.paymentKey());
    }

    private void validatePayable(String orderId, int amount) {
        if (!orderPaymentPort.isPayable(orderId, amount)) {
            throw new IllegalArgumentException("주문이 결제 가능한 상태가 아닙니다.");
        }
    }

    private void validateApprovalStatus(String approvalStatus) {
        if (!paymentGatewayPort.validateApproval(approvalStatus)) {
            throw new IllegalArgumentException("결제 승인에 실패했습니다.");
        }
    }
}