package learn.commerce.payment.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PaymentMethodTest {

    public static Stream<Arguments> validMethodSources() {
        return Stream.of(
                Arguments.of(PaymentMethod.CARD, "카드")
        );
    }

    @ParameterizedTest
    @MethodSource("validMethodSources")
    void 대소문자_구분없이_메소드_전환_가능(PaymentMethod method, String methodText) {
        // when
        PaymentMethod paymentMethod = PaymentMethod.of(methodText);

        // then
        assertThat(paymentMethod).isEqualTo(method);
    }

    @Test
    void 유효한_메서드가_아니라면_예외_발생() {
        // when & then
        assertThatThrownBy(() -> PaymentMethod.of("none"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}