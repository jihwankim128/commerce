package learn.commerce.order.adapter.in.context;

import learn.commerce.common.domain.Money;
import learn.commerce.order.application.port.in.UpdateOrderUseCase;
import learn.commerce.order.application.port.out.OrderQueryPort;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.payment.application.port.out.OrderPaymentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentAdapter implements OrderPaymentPort {

    private final OrderQueryPort orderQueryPort;
    private final UpdateOrderUseCase updateOrderUseCase;

    @Override
    public boolean isPayable(String orderId, int amount) {
        return orderQueryPort.isPayable(OrderId.from(orderId), new Money(amount));
    }

    @Override
    public void completePayment(String orderId, String paymentId) {
        updateOrderUseCase.complete(OrderId.from(orderId), paymentId);
    }
}
