package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import learn.commerce.common.domain.Money;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderItems;
import learn.commerce.order.domain.vo.OrderStatus;
import learn.commerce.order.domain.vo.Orderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTest {

    private static final OrderId validOrderId = OrderId.generate();
    private static final Orderer validOrderer = new Orderer("김지환", "01012345678");
    private static final OrderItems validOrderItems =
            new OrderItems(List.of(OrderItem.of(UUID.randomUUID(), "상품", new Money(1000), 5)));

    public static Stream<Arguments> invalidOrders() {
        return Stream.of(
                Arguments.of(null, validOrderer, validOrderItems),
                Arguments.of(validOrderId, null, validOrderItems),
                Arguments.of(validOrderId, validOrderer, null)
        );
    }

    private static OrderItems createOrderItemAsCount(int count) {
        List<OrderItem> items = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            items.add(OrderItem.of(UUID.randomUUID(), "상품" + i, new Money(1000), 2));
        }
        return new OrderItems(items);
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

    @ParameterizedTest
    @CsvSource(value = {"5000, true", "4000, false"})
    void 주문_금액으로_결제_가능_상태인지_확인_할_수_있다(int amount, boolean expected) {
        // given
        Order order = Order.createOf(validOrderId, validOrderer, validOrderItems);

        // when
        boolean payable = order.isPayable(new Money(amount));

        // then
        assertThat(payable).isEqualTo(expected);
    }

    @Test
    void 결제_식별자가_주어지면_주문을_완료_처리_할_수_있다() {
        // given
        Order order = Order.createOf(validOrderId, validOrderer, validOrderItems);

        // when
        order.completePaid("paymentId");

        // then
        assertThat(order.getPaymentId()).isEqualTo("paymentId");
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAYMENT_FULL_FILL);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 주문_완료_처리에_결제_식별자는_정보는_필수이다(String invalidPaymentId) {
        // given
        Order order = Order.createOf(validOrderId, validOrderer, validOrderItems);

        // when & then
        assertThatThrownBy(() -> order.completePaid(invalidPaymentId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 빈_상품_아이디로_주문을_취소한다면_전체_주문_취소가_된다() {
        // given
        Order order = Order.createOf(validOrderId, validOrderer, validOrderItems);

        // when
        order.cancel(List.of());

        // then
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER_CANCELED);
        assertThat(order.calculateRemainingAmount().amount()).isEqualTo(0);
    }

    @Test
    void 상품_아이디로_주문을_취소한다면_부분_취소가_된다() {
        // given: 단위 가격: 1000 * 수량: 2인 아이템 2개 = 총 4000원
        OrderItems items = createOrderItemAsCount(2);
        Order order = Order.createOf(validOrderId, validOrderer, items);

        // when: 1번 아이템 취소
        order.cancel(List.of(items.items().getFirst().getId()));

        // then: 4000원 - 2000원(1개) = 2000원
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PARTIAL_CANCELED);
        assertThat(order.calculateRemainingAmount().amount()).isEqualTo(2000);
    }
}