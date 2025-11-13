package learn.commerce.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import learn.commerce.common.domain.Money;
import learn.commerce.payment.domain.vo.PaymentMethod;
import learn.commerce.payment.domain.vo.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PaymentTest {

    private static final String VALID_ORDER_ID = UUID.randomUUID().toString();
    private static final Money VALID_AMOUNT = new Money(10000);
    private static final PaymentMethod VALID_METHOD = PaymentMethod.CARD;
    private static final String VALID_PAYMENT_KEY = "paymentKey";

    @Test
    void 결제정보_승인시_결제_완료_상태이다() {
        // when
        Payment payment = Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, VALID_PAYMENT_KEY);

        // then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.DONE);
    }

    @Test
    void 결제정보_승인시_가격정보가_없으면_예외_발생() {
        // when & then
        assertThatThrownBy(() -> Payment.approve(VALID_ORDER_ID, null, VALID_METHOD, VALID_PAYMENT_KEY))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 결제_승인시_결제_식별자가_없으면_예외_발생(String invalidPaymentKey) {
        // when & then
        assertThatThrownBy(() -> Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, invalidPaymentKey))
                .isInstanceOf(IllegalArgumentException.class);
    }
}