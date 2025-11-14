package learn.commerce.order.domain;

import java.util.List;
import learn.commerce.common.domain.Money;
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
    private OrderStatus status;
    private String paymentId;

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

    public boolean isPayable(Money amount) {
        return totalAmount.equals(amount) && status.isComplete();
    }

    public void complete(String paymentId) {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("결제 식별자는 필수 정보입니다.");
        }
        this.paymentId = paymentId;
        this.status = OrderStatus.PAYMENT_FULL_FILL;
    }
}
