package learn.commerce.order.application.port.out;

import learn.commerce.common.domain.Money;
import learn.commerce.order.domain.vo.OrderId;

public interface OrderQueryPort {
    boolean isPayable(OrderId orderId, Money amount);
}