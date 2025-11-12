package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import learn.commerce.order.domain.vo.Money;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderItem;
import learn.commerce.order.domain.vo.OrderItems;
import learn.commerce.order.domain.vo.OrderStatus;
import learn.commerce.order.domain.vo.Orderer;
import learn.commerce.order.domain.vo.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrderTest {

    private static final OrderId validOrderId = OrderId.generate();
    private static final Orderer validOrderer = new Orderer("김지환", "01012345678");
    private static final OrderItems validOrderItems =
            new OrderItems(List.of(OrderItem.of(new Product(UUID.randomUUID(), "상품"), new Money(1000), 5)));

    public static Stream<Arguments> invalidOrders() {
        return Stream.of(
                Arguments.of(null, validOrderer, validOrderItems),
                Arguments.of(validOrderId, null, validOrderItems),
                Arguments.of(validOrderId, validOrderer, null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidOrders")
    void 주문_정보는_필수정보이다(OrderId orderId, Orderer orderer, OrderItems items) {
        // when & then
        assertThatThrownBy(() -> Order.createOf(orderId, orderer, items))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문_생성시_전체_주문가격이_계산된_채로_주문완료가_된다() {
        // when
        Order order = Order.createOf(validOrderId, validOrderer, validOrderItems);

        // then
        assertThat(order.getTotalAmount()).isEqualTo(new Money(5000));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETED);
    }
}