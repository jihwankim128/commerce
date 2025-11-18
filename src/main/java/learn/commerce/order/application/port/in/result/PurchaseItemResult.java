package learn.commerce.order.application.port.in.result;

import learn.commerce.order.domain.OrderItem;

public record PurchaseItemResult(
        String productId,
        String productName,
        int price,
        int quantity,
        int amount,
        String status
) {
    public static PurchaseItemResult from(OrderItem orderItem) {
        return new PurchaseItemResult(
                orderItem.getId().toString(),
                orderItem.getName(),
                orderItem.getPrice().amount(),
                orderItem.getQuantity(),
                orderItem.getTotalAmount().amount(),
                orderItem.getStatus().name()
        );
    }
}