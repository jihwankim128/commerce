package learn.commerce.order.application.port.in.command;

import java.util.List;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderItem;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderItems;
import learn.commerce.order.domain.vo.Orderer;

public record PurchaseOrder(String ordererName, String ordererPhoneNumber, List<PurchaseOrderItem> items) {

    public Order toDomain() {
        List<OrderItem> items = toOrderItems();
        return Order.createOf(
                OrderId.generate(),
                new Orderer(ordererName, ordererPhoneNumber),
                new OrderItems(items)
        );
    }

    private List<OrderItem> toOrderItems() {
        return this.items.stream()
                .map(PurchaseOrderItem::toDomain)
                .toList();
    }
}
