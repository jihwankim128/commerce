package learn.commerce.order.domain.vo;

import java.util.List;
import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.order.domain.OrderItem;

public record OrderItems(List<OrderItem> items) {
    public OrderItems {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목은 최소 1개 이상이어야 합니다");
        }
    }

    public Money calculateTotalAmount() {
        return items.stream()
                .map(OrderItem::getTotalAmount)
                .reduce(Money.ZERO, Money::add);
    }

    public void cancelPartial(List<UUID> productIds) {
        items.forEach(item -> {
            if (item.matches(productIds)) {
                item.cancel();
            }
        });
    }

    public void cancelAll() {
        items.forEach(OrderItem::cancel);
    }

    public boolean isAllCancel() {
        long canceledCount = items.stream().filter(OrderItem::isCancel).count();
        return canceledCount == items.size();
    }

    public Money calculateCanceledAmount() {
        return items.stream()
                .filter(OrderItem::isCancel)
                .map(OrderItem::getTotalAmount)
                .reduce(Money.ZERO, Money::add);
    }
}
