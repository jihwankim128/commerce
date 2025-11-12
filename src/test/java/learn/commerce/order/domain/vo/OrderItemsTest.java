package learn.commerce.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderItemsTest {

    private static List<OrderItem> createOrderItems() {
        return List.of(
                new OrderItem(new Product(UUID.randomUUID(), "상품1"), new Money(10000), 2),
                new OrderItem(new Product(UUID.randomUUID(), "상품2"), Money.ZERO, 3)
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 주문_항목은_최소_1개_이상이어야_한다(List<OrderItem> items) {
        // when
        assertThatThrownBy(() -> new OrderItems(items))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문_항목들의_전체_가격을_구할_수_있다() {
        // given
        OrderItems orderItems = new OrderItems(createOrderItems());

        // when
        Money amount = orderItems.calculateTotalAmount();

        // then
        assertThat(amount.amount()).isEqualTo(20000);
    }
}