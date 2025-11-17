package learn.commerce.order.application.port.in.command;

import learn.commerce.common.domain.Money;
import learn.commerce.common.utils.UuidConverter;
import learn.commerce.order.domain.OrderItem;

public record PurchaseOrderItem(
        String productId,
        String productName,
        int price,
        int quantity
) {

    public OrderItem toDomain() {
        return OrderItem.of(
                UuidConverter.fromString(productId),
                productName,
                new Money(price),
                quantity
        );
    }
}
