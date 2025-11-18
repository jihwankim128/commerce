package learn.commerce.order.adapter.in.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import learn.commerce.order.application.port.in.command.OrderCancellation;

public record CancelOrderRequest(
        @NotNull List<UUID> productIds,
        @NotBlank(message = "취소 사유는 필수입니다.") String cancelReason
) {
    public OrderCancellation toCommand(UUID orderId) {
        return new OrderCancellation(orderId, productIds, cancelReason);
    }
}
