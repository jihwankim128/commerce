package learn.commerce.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import learn.commerce.order.domain.vo.Orderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrdererTest {

    private static Stream<Arguments> invalidOrdererProvider() {
        return Stream.of(
                // 이름 필수 검증
                Arguments.of(null, "01012345678", "주문자 이름은 필수입니다"),
                Arguments.of("", "01012345678", "주문자 이름은 필수입니다"),
                Arguments.of(" ", "01012345678", "주문자 이름은 필수입니다"),

                // 전화번호 형식 검증
                Arguments.of("홍길동", null, "올바른 전화번호 형식이 아닙니다"),
                Arguments.of("홍길동", "01198765432", "올바른 전화번호 형식이 아닙니다"), // 010 아님
                Arguments.of("홍길동", "010-1234-5678", "올바른 전화번호 형식이 아닙니다"), // 하이픈 포함
                Arguments.of("홍길동", "0101234567", "올바른 전화번호 형식이 아닙니다"), // 10자리
                Arguments.of("홍길동", "010123456789", "올바른 전화번호 형식이 아닙니다") // 12자리
        );
    }

    @ParameterizedTest(name = "{0}, {1}로 Orderer 생성 시 실패해야 함")
    @MethodSource("invalidOrdererProvider")
    void 유효하지_않은_주문자_정보로_Order_생성시_실패(String name, String phoneNumber, String expectedMessage) {
        // When & Then
        assertThatThrownBy(() -> new Orderer(name, phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void 유효한_주문자_정보로_Orderer_생성에_성공한다() {
        // Given
        String validName = "김철수";
        String validPhoneNumber = "01099998888";

        // When
        Orderer orderer = new Orderer(validName, validPhoneNumber);

        // then
        assertThat(orderer.name()).isEqualTo(validName);
        assertThat(orderer.phoneNumber()).isEqualTo(validPhoneNumber);
    }
}