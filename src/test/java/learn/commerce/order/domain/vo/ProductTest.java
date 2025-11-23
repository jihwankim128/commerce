package learn.commerce.order.domain.vo;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final String VALID_NAME = "테스트 상품";

    private static Stream<Arguments> invalidProductProvider() {
        return Stream.of(
                Arguments.of(null, VALID_NAME, "상품 ID는 필수입니다"),
                Arguments.of(VALID_ID, null, "상품명은 필수입니다"),
                Arguments.of(VALID_ID, "", "상품명은 필수입니다"),
                Arguments.of(VALID_ID, " ", "상품명은 필수입니다")
        );
    }

    @ParameterizedTest(name = "ID: {0}, Name: {1} 생성 시 실패해야 함")
    @MethodSource("invalidProductProvider")
    void Product_생성_시_필수_정보가_누락되면_예외가_발생한다(UUID id, String name, String expectedMessage) {
        // When & Then
        assertThatThrownBy(() -> new Product(id, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void 유효한_상품ID와_상품명으로_Prdouct_생성에_성공한다() {
        // When & Then
        assertThatCode(() -> new Product(VALID_ID, VALID_NAME))
                .doesNotThrowAnyException();
    }
}