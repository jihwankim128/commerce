package learn.commerce.payment.application;

import static learn.commerce.payment.application.stub.ApprovalUseCaseStub.결제_승인_미승인_스텁;
import static learn.commerce.payment.application.stub.ApprovalUseCaseStub.결제_승인_성공_스텁;
import static learn.commerce.payment.application.stub.ApprovalUseCaseStub.미주문_결제_스텁;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.out.OrderPaymentPort;
import learn.commerce.payment.application.port.out.PaymentGatewayPort;
import learn.commerce.payment.application.service.PaymentService;
import learn.commerce.payment.domain.Payment;
import learn.commerce.payment.domain.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentGatewayPort paymentGatewayPort;
    @Mock
    private OrderPaymentPort orderPaymentPort;
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService service;

    @Test
    void 결제_승인_응답중_예외가_발생하면_주문을_취소한다() {
        // given : 결제 승인 호출 시 예외 발생
        PaymentApproval mockCommand = 미주문_결제_스텁(orderPaymentPort);

        // when & then : 결제 취소
        assertThatThrownBy(() -> service.approvePayment(mockCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("주문이 결제 가능한 상태가 아닙니다.");
    }

    @Test
    void 결제_승인_응답_결과가_승인상태가_아니라면_주문_취소() {
        // given : 결제 승인 호출 시 예외 발생
        PaymentApproval mockCommand = 결제_승인_미승인_스텁(paymentGatewayPort, orderPaymentPort);

        // when & then : 결제 취소
        assertThatThrownBy(() -> service.approvePayment(mockCommand))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("결제 승인에 실패했습니다");
    }

    @Test
    void 결제_승인_응답_결과가_승인상태라면_주문_완료() {
        // given
        PaymentApproval mockCommand = 결제_승인_성공_스텁(paymentGatewayPort, orderPaymentPort);

        // when
        service.approvePayment(mockCommand);

        // then
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderPaymentPort, times(1)).completePayment(anyString(), anyString());
    }
}