package learn.commerce.order.adapter.in.web.request;

import learn.commerce.order.application.port.in.command.PurchaseOrderItem;

public record PurchaseOrderItemRequest(
        String productId,
        String productName,
        int price,
        int quantity
) {
    public PurchaseOrderItem toCommand() {
        return new PurchaseOrderItem(
                productId,
                productName,
                price,
                quantity
        );
    }
}