package learn.commerce.order.application.port.in;

import learn.commerce.order.domain.vo.OrderId;

public interface UpdateOrderUseCase {
    void complete(OrderId orderId, String paymentId);
}