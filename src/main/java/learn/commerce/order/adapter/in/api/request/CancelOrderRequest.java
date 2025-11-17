package learn.commerce.order.adapter.in.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CancelOrderRequest(
        @NotNull List<String> productIds,
        @NotBlank(message = "취소 사유는 필수입니다.") String cancelReason
) {
}
