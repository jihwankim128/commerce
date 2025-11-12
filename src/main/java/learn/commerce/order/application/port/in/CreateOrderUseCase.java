package learn.commerce.order.application.port.in;

import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.result.PurchaseResult;

public interface CreateOrderUseCase {

    PurchaseResult createOrder(PurchaseOrder command);
}
