package learn.commerce.order.application.port.in.command;

import learn.commerce.order.domain.vo.Money;
import learn.commerce.order.domain.vo.OrderItem;
import learn.commerce.order.domain.vo.Product;

public record PurchaseOrderItem(
        String productId,
        String productName,
        int price,
        int quantity
) {

    public OrderItem toDomain() {
        return OrderItem.of(
                Product.from(productId, productName),
                new Money(price),
                quantity
        );
    }
}
