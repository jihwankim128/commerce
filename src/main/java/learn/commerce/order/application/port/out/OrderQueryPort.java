package learn.commerce.order.application.port.out;

import java.util.List;
import learn.commerce.common.domain.Money;
import learn.commerce.order.adapter.in.web.response.OrderResponse;
import learn.commerce.order.domain.vo.OrderId;

public interface OrderQueryPort {
    boolean isPayable(OrderId orderId, Money amount);

    OrderResponse getOrder(String orderId);

    List<OrderResponse> getOrders();
}