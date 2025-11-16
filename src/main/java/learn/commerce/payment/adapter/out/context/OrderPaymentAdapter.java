package learn.commerce.payment.adapter.out.context;

import learn.commerce.common.domain.Money;
import learn.commerce.order.application.port.out.OrderQueryPort;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.payment.application.port.out.OrderPaymentPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPaymentAdapter implements OrderPaymentPort {

    private final OrderQueryPort orderQueryPort;

    @Override
    public boolean isPayable(String orderId, int amount) {
        return orderQueryPort.isPayable(OrderId.from(orderId), new Money(amount));
    }
}
