package learn.commerce.order.application.port.in;

import learn.commerce.order.application.port.in.command.OrderCancellation;

public interface CancelOrderUseCase {
    void cancel(OrderCancellation command);
}