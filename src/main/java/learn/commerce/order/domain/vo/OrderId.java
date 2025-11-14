package learn.commerce.order.domain.vo;

import java.util.UUID;
import learn.commerce.common.utils.UuidConverter;

public record OrderId(UUID value) {
    public OrderId {
        if (value == null) {
            throw new IllegalArgumentException("주문 ID는 필수 정보입니다.");
        }
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId from(String orderId) {
        return new OrderId(UuidConverter.fromString(orderId));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
