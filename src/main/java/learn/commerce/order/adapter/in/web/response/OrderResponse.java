package learn.commerce.order.adapter.in.web.response;

import java.util.List;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import learn.commerce.order.domain.Order;

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

    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getOrderId().toString(),
                order.getOrderer().name(),
                order.getOrderer().phoneNumber(),
                order.getTotalAmount().amount(),
                order.getStatus().name(),
                order.getItems().stream()
                        .map(OrderItemResponse::from)
                        .toList()
        );
    }
}