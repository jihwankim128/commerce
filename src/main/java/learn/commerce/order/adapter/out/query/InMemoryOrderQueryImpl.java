package learn.commerce.order.adapter.out.query;

import learn.commerce.common.domain.Money;
import learn.commerce.order.application.port.out.OrderQueryPort;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryOrderQueryImpl implements OrderQueryPort {

    private final OrderRepository orderRepository;

    @Override
    public boolean isPayable(OrderId orderId, Money amount) {
        Order order = orderRepository.getByIdWithThrow(orderId);
        return order.isPayable(amount);
    }
}
