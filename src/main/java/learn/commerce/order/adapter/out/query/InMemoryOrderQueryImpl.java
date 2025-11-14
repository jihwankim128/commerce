package learn.commerce.order.adapter.out.query;

import java.util.List;
import learn.commerce.common.domain.Money;
import learn.commerce.order.adapter.in.web.response.OrderResponse;
import learn.commerce.order.application.port.out.OrderQueryPort;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderStatus;
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

    @Override
    public OrderResponse getOrder(String orderId) {
        Order order = orderRepository.getByIdWithThrow(OrderId.from(orderId));
        return OrderResponse.from(order);
    }

    @Override
    public List<OrderResponse> getOrders() {
        return orderRepository.getOrders()
                .stream()
                .filter(order -> order.getStatus() != OrderStatus.ORDER_COMPLETED)
                .map(OrderResponse::from)
                .toList();
    }
}
