package learn.commerce.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.order.domain.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderItemsTest {

    private static List<OrderItem> createOrderItems() {
        return List.of(
                OrderItem.of(UUID.randomUUID(), "상품1", new Money(10000), 2),
                OrderItem.of(UUID.randomUUID(), "상품2", new Money(5000), 3)
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
        assertThat(amount.amount()).isEqualTo(35000);
    }

    @Test
    void 주문_항목들이_모두_취소되었다() {
        // given: 총 35,000원 아이템
        OrderItems orderItems = new OrderItems(createOrderItems());

        // when
        orderItems.cancelAll();

        // then: 전체 35,000원 취소
        assertThat(orderItems.isAllCancel()).isTrue();
        assertThat(orderItems.calculateCanceledAmount().amount()).isEqualTo(35000);
    }

    @Test
    void 주문_항목들이_부분_취소되었다() {
        // given: 총 35,000원 아이템
        OrderItems orderItems = new OrderItems(createOrderItems());
        List<UUID> productIds = List.of(orderItems.items().getFirst().getId());

        // when: 첫번째 아이템 취소
        orderItems.cancelPartial(productIds);

        // then: 20,000원 취소
        assertThat(orderItems.isAllCancel()).isFalse();
        assertThat(orderItems.calculateCanceledAmount().amount()).isEqualTo(20000);
    }
}