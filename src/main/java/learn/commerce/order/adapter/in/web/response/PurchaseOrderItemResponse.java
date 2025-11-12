package learn.commerce.order.adapter.in.web.response;

import learn.commerce.order.application.port.in.result.PurchaseItemResult;

public record PurchaseOrderItemResponse(
        String productId,
        String productName,
        int price,
        int quantity,
        int amount
) {
    public static PurchaseOrderItemResponse from(PurchaseItemResult result) {
        return new PurchaseOrderItemResponse(
                result.productId(),
                result.productName(),
                result.price(),
                result.quantity(),
                result.amount()
        );
    }
}