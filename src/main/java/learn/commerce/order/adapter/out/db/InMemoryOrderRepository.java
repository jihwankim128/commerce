package learn.commerce.order.adapter.out.db;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<OrderId, Order> store = new ConcurrentHashMap<>();

    @Override
    public Order save(Order order) {
        store.put(order.getOrderId(), order);
        return order;
    }

    @Override
    public Order getByIdWithThrow(OrderId orderId) {
        return Optional.ofNullable(store.get(orderId))
                .orElseThrow(() -> new IllegalArgumentException("주문 정보가 없습니다."));
    }

    @Override
    public List<Order> getOrders() {
        return store.values()
                .stream()
                .toList();
    }
}
