package learn.commerce.order.adapter.in.web.response;

import java.util.List;
import learn.commerce.order.application.port.in.result.PurchaseResult;

public record OrderResponse(
        String orderId,
        String ordererName,
        String ordererPhoneNumber,
        int totalAmount,
        String status,
        List<OrderItemResponse> items
) {
    public static OrderResponse from(PurchaseResult result) {
        return new OrderResponse(
                result.orderId(),
                result.ordererName(),
                result.ordererPhoneNumber(),
                result.totalAmount(),
                result.status(),
                result.items().stream()
                        .map(OrderItemResponse::from)
                        .toList()
        );
    }
}