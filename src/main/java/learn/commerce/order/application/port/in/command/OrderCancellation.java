package learn.commerce.order.application.port.in.command;

import java.util.List;
import java.util.UUID;
import learn.commerce.order.domain.vo.OrderId;

public record OrderCancellation(
        UUID orderId,
        List<UUID> productIds,
        String cancelReason
) {
    public OrderId toOrderId() {
        return new OrderId(orderId);
    }
}