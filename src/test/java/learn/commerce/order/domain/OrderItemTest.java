package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import java.util.stream.Stream;
import learn.commerce.order.domain.vo.Money;
import learn.commerce.order.domain.vo.OrderItem;
import learn.commerce.order.domain.vo.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderItemTest {
    private static final Product VALID_PRODUCT = new Product(UUID.randomUUID(), "상품명");
    private static final Money VALID_PRICE = new Money(1000);
    private static final int VALID_QUANTITY = 10;

    public static Stream<Arguments> provideInvalidProductAndPrice() {
        return Stream.of(
                Arguments.of(null, VALID_PRICE),
                Arguments.of(VALID_PRODUCT, null)
        );
    }

    @Test
    void 상품정보_가격정보_수량정보로_주문_상품을_생성한다() {
        // when
        OrderItem orderItem = new OrderItem(VALID_PRODUCT, VALID_PRICE, VALID_QUANTITY);

        // then
        assertThat(orderItem.product()).isEqualTo(VALID_PRODUCT);
        assertThat(orderItem.price()).isEqualTo(VALID_PRICE);
        assertThat(orderItem.quantity()).isEqualTo(VALID_QUANTITY);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidProductAndPrice")
    void 상품정보와_가격정보는_필수이다(Product invalidProduct, Money invalidPrice) {
        // when & then
        assertThatThrownBy(() -> new OrderItem(invalidProduct, invalidPrice, VALID_QUANTITY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    void 수량정보는_1개_이상이어야한다(int invalidQuantity) {
        // when & then
        assertThatThrownBy(() -> new OrderItem(VALID_PRODUCT, VALID_PRICE, invalidQuantity))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문_상품의_총_가격을_알_수_있다() {
        // given
        OrderItem orderItem = new OrderItem(VALID_PRODUCT, VALID_PRICE, VALID_QUANTITY);

        // when
        Money amount = orderItem.calculateTotalAmount();

        // then
        assertThat(amount.amount()).isEqualTo(10000);
    }
}