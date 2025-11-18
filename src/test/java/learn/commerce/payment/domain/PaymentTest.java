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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = " ")
    void 결제_승인시_결제_식별자가_없으면_예외_발생(String invalidPaymentKey) {
        // when & then
        assertThatThrownBy(() -> Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, invalidPaymentKey))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 전체_결제_취소시_결제_취소상태가_된다() {
        // given
        Payment payment = Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, VALID_PAYMENT_KEY);

        // when
        payment.cancel(new Money(10000), "전체 취소");

        // then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.CANCELED);
    }

    @Test
    void 결제_취소_상태일_때_결제를_취소하면_예외가_발생한다() {
        // given
        Payment payment = Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, VALID_PAYMENT_KEY);
        payment.cancel(new Money(10000), "전체 취소");

        // when & then
        assertThatThrownBy(() -> payment.cancel(new Money(0), "재취소"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 부분_결제_취소시_여전히_결제_완료_상태이다() {
        // given
        Payment payment = Payment.approve(VALID_ORDER_ID, VALID_AMOUNT, VALID_METHOD, VALID_PAYMENT_KEY);

        // when
        payment.cancel(new Money(5000), "부분 취소");

        // then
        assertThat(payment.getStatus()).isEqualTo(PaymentStatus.DONE);
    }
}