package learn.commerce.order.domain.vo;

import java.util.List;

public record OrderItems(List<OrderItem> items) {
    public OrderItems {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 최소 1개 이상이어야 합니다");
        }
    }

    public Money calculateTotalAmount() {
        return items.stream()
                .map(OrderItem::calculateTotalAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
