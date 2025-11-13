package learn.commerce.order.adapter.in.web.response;

import learn.commerce.order.application.port.in.result.PurchaseItemResult;

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
}