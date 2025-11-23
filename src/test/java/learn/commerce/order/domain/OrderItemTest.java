package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import learn.commerce.common.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderItemTest {
    private static final UUID VALID_ID = UUID.randomUUID();
    private static final String VALID_NAME = "상품명";
    private static final Money VALID_PRICE = new Money(1000);
    private static final int VALID_QUANTITY = 10;

    public static Stream<Arguments> provideInvalidItemAndPrice() {
        return Stream.of(
                Arguments.of(null, VALID_NAME, VALID_PRICE),
                Arguments.of(VALID_ID, null, VALID_PRICE),
                Arguments.of(VALID_ID, "", VALID_PRICE),
                Arguments.of(VALID_ID, " ", VALID_PRICE),
                Arguments.of(VALID_ID, VALID_NAME, null)
        );
    }

    public static Stream<Arguments> provideMatchCases() {
        return Stream.of(
                Arguments.of(List.of(), false),
                Arguments.of(List.of(VALID_ID), true)
        );
    }

    @Test
    void 상품정보_가격정보_수량정보로_주문_상품을_생성한다() {
        // when
        OrderItem orderItem = OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, VALID_QUANTITY);

        // then
        assertThat(orderItem.getId()).isEqualTo(VALID_ID);
        assertThat(orderItem.getName()).isEqualTo(VALID_NAME);
        assertThat(orderItem.getPrice()).isEqualTo(VALID_PRICE);
        assertThat(orderItem.getQuantity()).isEqualTo(VALID_QUANTITY);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidItemAndPrice")
    void 상품정보와_가격정보는_필수이다(UUID invalidId, String invalidName, Money invalidPrice) {
        // when & then
        assertThatThrownBy(() -> OrderItem.of(invalidId, invalidName, invalidPrice, VALID_QUANTITY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void 수량정보는_1개_이상이어야한다(int invalidQuantity) {
        // when & then
        assertThatThrownBy(() -> OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, invalidQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문_상품의_총_가격을_알_수_있다() {
        // when
        OrderItem orderItem = OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, VALID_QUANTITY);

        // then
        assertThat(orderItem.getTotalAmount().amount()).isEqualTo(10000);
    }

    @ParameterizedTest
    @MethodSource("provideMatchCases")
    void 상품_식별자에_포함된_주문_상품인지_알_수_있다(List<UUID> productIds, boolean expected) {
        // given
        OrderItem orderItem = OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, VALID_QUANTITY);

        // when
        boolean matches = orderItem.matches(productIds);

        // then
        assertThat(matches).isEqualTo(expected);
    }

    @Test
    void 주문_상품을_취소한다() {
        // given
        OrderItem orderItem = OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, VALID_QUANTITY);

        // when
        orderItem.cancel();

        // then
        assertThat(orderItem.isCancel()).isTrue();
    }

    @Test
    void 이미_취소한_상품을_재취소_할_경우_예외가_발생한다() {
        // given
        OrderItem orderItem = OrderItem.of(VALID_ID, VALID_NAME, VALID_PRICE, VALID_QUANTITY);
        orderItem.cancel();

        // when & then
        assertThatThrownBy(orderItem::cancel)
                .isInstanceOf(IllegalArgumentException.class);
    }
}