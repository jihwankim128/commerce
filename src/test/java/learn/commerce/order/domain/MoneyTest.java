package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import learn.commerce.order.domain.vo.Money;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void 금액은_0원_이상이어야_한다() {
        // when & then
        assertThatThrownBy(() -> new Money(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액_간_더할_수_있다() {
        // given
        Money baseMoney = new Money(1000);
        Money addMoney = new Money(1000);

        // when
        Money money = baseMoney.add(addMoney);

        // then
        assertThat(money.amount()).isEqualTo(2000);
    }

    @Test
    void 금액_간_더할_때_금액의_한도를_초과할_수_없다() {
        // given
        Money baseMoney = new Money((int) 1e9);
        Money addMoney = new Money((int) 1e9 * 2);

        // when & then
        assertThatThrownBy(() -> baseMoney.add(addMoney))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 금액을_곱할_수_있다() {
        // given
        Money baseMoney = new Money(1000);

        // when
        Money money = baseMoney.multiply(2);

        // then
        assertThat(money.amount()).isEqualTo(2000);
    }

    @Test
    void 금액을_곱할_때_금액의_한도를_초과할_수_없다() {
        // given
        Money baseMoney = new Money((int) 1e9);

        // when & then
        assertThatThrownBy(() -> baseMoney.multiply(3))
                .isInstanceOf(IllegalArgumentException.class);
    }
}