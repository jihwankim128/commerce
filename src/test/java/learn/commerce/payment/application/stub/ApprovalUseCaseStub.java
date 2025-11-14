package learn.commerce.payment.application.stub;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.dto.result.PaymentApprovalResult;
import learn.commerce.payment.application.port.out.OrderPaymentPort;
import learn.commerce.payment.application.port.out.PaymentGatewayPort;
import learn.commerce.payment.domain.Payment;

public class ApprovalUseCaseStub {

    public static PaymentApproval 결제_승인_성공_스텁(PaymentGatewayPort pgPort, OrderPaymentPort orderPort) {
        when(orderPort.isPayable(anyString(), anyInt())).thenReturn(true);

        PaymentApproval mockCommand = mock(PaymentApproval.class);
        when(mockCommand.orderId()).thenReturn("orderId");
        when(mockCommand.amount()).thenReturn(1000);
        when(mockCommand.paymentKey()).thenReturn("paymentKey");

        Payment mockPayment = mock(Payment.class);
        when(mockCommand.toDomain(anyString())).thenReturn(mockPayment);

        PaymentApprovalResult mockResult = mock(PaymentApprovalResult.class);
        when(mockResult.status()).thenReturn("status");
        when(mockResult.method()).thenReturn("method");
        when(pgPort.callApproval(any(PaymentApproval.class))).thenReturn(mockResult);
        when(pgPort.validateApproval(anyString())).thenReturn(true);
        return mockCommand;
    }

    public static PaymentApproval 결제_승인_미승인_스텁(PaymentGatewayPort pgPort, OrderPaymentPort orderPort) {
        when(orderPort.isPayable(anyString(), anyInt())).thenReturn(true);

        PaymentApproval mockCommand = mock(PaymentApproval.class);
        when(mockCommand.orderId()).thenReturn("orderId");
        when(mockCommand.amount()).thenReturn(1000);

        PaymentApprovalResult mockResult = mock(PaymentApprovalResult.class);
        when(mockResult.status()).thenReturn("status");

        when(pgPort.callApproval(any(PaymentApproval.class))).thenReturn(mockResult);
        when(pgPort.validateApproval(anyString())).thenReturn(false);
        return mockCommand;
    }

    public static PaymentApproval 미주문_결제_스텁(OrderPaymentPort orderPort) {
        PaymentApproval mockCommand = mock(PaymentApproval.class);
        when(mockCommand.orderId()).thenReturn("orderId");
        when(mockCommand.amount()).thenReturn(1000);
        when(orderPort.isPayable(anyString(), anyInt())).thenReturn(false);
        return mockCommand;
    }
}
