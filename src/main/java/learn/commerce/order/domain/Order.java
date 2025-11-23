package learn.commerce.order.domain;

import java.util.List;
import learn.commerce.order.domain.vo.Money;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderItem;
import learn.commerce.order.domain.vo.OrderItems;
import learn.commerce.order.domain.vo.OrderStatus;
import learn.commerce.order.domain.vo.Orderer;
import lombok.Getter;

@Getter
public class Order {
    private final OrderId orderId;
    private final Orderer orderer;
    private final OrderItems items;
    private final Money totalAmount;
    private final OrderStatus status;

    private Order(OrderId orderId, Orderer orderer, OrderItems items) {
        if (orderId == null || orderer == null || items == null) {
            throw new IllegalArgumentException("주문 정보는 필수 정보입니다.");
        }
        this.orderId = orderId;
        this.orderer = orderer;
        this.items = items;
        this.totalAmount = items.calculateTotalAmount();
        this.status = OrderStatus.ORDER_COMPLETED;
    }

    public static Order createOf(OrderId orderId, Orderer orderer, OrderItems items) {
        return new Order(orderId, orderer, items);
    }

    public List<OrderItem> getItems() {
        return items.items();
    }
}
