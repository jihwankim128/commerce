package learn.commerce.order.domain;

import java.util.List;
import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.order.domain.vo.OrderItemStatus;
import lombok.Getter;

@Getter
public class OrderItem {
    private final UUID id;
    private final String name;
    private final Money price;
    private final int quantity;
    private final Money totalAmount;
    private OrderItemStatus status;

    public OrderItem(UUID id, String name, Money price, int quantity) {
        if (id == null || price == null || name == null || name.isBlank()) {
            throw new IllegalArgumentException("주문 상품은 필수 정보입니다.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("상품 수량은 1개 이상이어야 합니다");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.totalAmount = price.multiply(quantity);
        this.status = OrderItemStatus.CONFIRMED;
    }

    public static OrderItem of(UUID id, String name, Money price, int quantity) {
        return new OrderItem(id, name, price, quantity);
    }

    public boolean matches(List<UUID> productIds) {
        return productIds.contains(id);
    }

    public void cancel() {
        if (isCancel()) {
            throw new IllegalArgumentException("이미 주문 취소된 아이템입니다.");
        }
        this.status = OrderItemStatus.CANCELED;
    }

    public boolean isCancel() {
        return status == OrderItemStatus.CANCELED;
    }
}
