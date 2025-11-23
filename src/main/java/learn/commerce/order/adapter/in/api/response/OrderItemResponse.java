package learn.commerce.order.adapter.in.api.response;

import learn.commerce.order.application.port.in.result.PurchaseItemResult;
import learn.commerce.order.domain.OrderItem;

public record OrderItemResponse(
        String productId,
        String productName,
        int price,
        int quantity,
        int amount,
        String status
) {
    public static OrderItemResponse from(PurchaseItemResult result) {
        return new OrderItemResponse(
                result.productId(),
                result.productName(),
                result.price(),
                result.quantity(),
                result.amount(),
                result.status()
        );
    }

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId().toString(),
                orderItem.getName(),
                orderItem.getPrice().amount(),
                orderItem.getQuantity(),
                orderItem.getTotalAmount().amount(),
                orderItem.getStatus().name()
        );
    }
}