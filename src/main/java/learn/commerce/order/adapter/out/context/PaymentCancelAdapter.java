package learn.commerce.order.adapter.out.context;

import learn.commerce.common.domain.Money;
import learn.commerce.order.application.port.out.CancelPaymentPort;
import learn.commerce.payment.application.port.dto.command.PaymentCancellation;
import learn.commerce.payment.application.port.in.CancelPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentCancelAdapter implements CancelPaymentPort {

    private final CancelPaymentUseCase cancelPaymentUseCase;

    @Override
    public void cancelPayment(String paymentKey, Money cancelAmount, String reason) {
        cancelPaymentUseCase.cancel(new PaymentCancellation(
                paymentKey,
                cancelAmount.amount(),
                reason
        ));
    }
}