package learn.commerce.order.adapter.in.web.response;

import learn.commerce.order.application.port.in.result.PurchaseItemResult;
import learn.commerce.order.domain.vo.OrderItem;

public record OrderItemResponse(
        String productId,
        String productName,
        int price,
        int quantity,
        int amount
) {
    public static OrderItemResponse from(PurchaseItemResult result) {
        return new OrderItemResponse(
                result.productId(),
                result.productName(),
                result.price(),
                result.quantity(),
                result.amount()
        );
    }

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.product().id().toString(),
                orderItem.product().name(),
                orderItem.price().amount(),
                orderItem.quantity(),
                orderItem.totalAmount().amount()
        );
    }
}