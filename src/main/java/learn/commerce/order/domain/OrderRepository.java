package learn.commerce.order.domain;

import java.util.List;
import learn.commerce.order.domain.vo.OrderId;

public interface OrderRepository {

    Order save(Order order);

    Order getByIdWithThrow(OrderId orderId);

    List<Order> getOrders();
}
