package learn.commerce.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
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
        assertThat(orderId.value().toString()).hasSize(36);
    }

    @Test
    void 문자열_UUID로_ORDER_ID를_생성한다() {
        // given
        String uuid = UUID.randomUUID().toString();

        // when
        OrderId orderId = OrderId.from(uuid);

        // then
        assertThat(orderId.value().toString()).isEqualTo(uuid);
    }
}