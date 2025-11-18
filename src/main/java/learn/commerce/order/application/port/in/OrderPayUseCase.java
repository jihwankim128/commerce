package learn.commerce.order.application.port.in;

import learn.commerce.order.domain.vo.OrderId;

public interface OrderPayUseCase {
    void completePaid(OrderId orderId, String paymentId);
}