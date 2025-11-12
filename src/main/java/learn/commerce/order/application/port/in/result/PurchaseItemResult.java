package learn.commerce.order.application.port.in.result;

import learn.commerce.order.domain.vo.OrderItem;

public record PurchaseItemResult(
        String productId,
        String productName,
        int price,
        int quantity,
        int amount
) {
    public static PurchaseItemResult from(OrderItem orderItem) {
        return new PurchaseItemResult(
                orderItem.product().id().toString(),
                orderItem.product().name(),
                orderItem.price().amount(),
                orderItem.quantity(),
                orderItem.totalAmount().amount()
        );
    }
}