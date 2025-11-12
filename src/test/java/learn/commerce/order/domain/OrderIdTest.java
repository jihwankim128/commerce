package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import learn.commerce.order.domain.vo.OrderId;
import org.junit.jupiter.api.Test;

class OrderIdTest {

    @Test
    void ORDER_ID는_필수_정보이다() {
        // when & then
        assertThatThrownBy(() -> new OrderId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void ORDER_ID를_생성한다() {
        // when
        OrderId orderId = OrderId.generate();

        // then
        assertThat(orderId.value()).isNotNull();
    }
}