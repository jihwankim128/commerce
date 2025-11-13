package learn.commerce.payment.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class PaymentIdTest {

    @Test
    void PAYMENT_ID는_필수_정보이다() {
        // when & then
        assertThatThrownBy(() -> new PaymentId(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void PAYMENT_ID를_생성한다() {
        // when
        PaymentId paymentId = PaymentId.generate();

        // then
        assertThat(paymentId.toString()).hasSize(36);
    }
}