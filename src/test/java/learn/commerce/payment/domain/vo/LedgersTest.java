package learn.commerce.payment.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import learn.commerce.common.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LedgersTest {

    private final Money initialAmount = new Money(50000);

    @Test
    void recordApproval은_승인_내역_1개를_가진_장부내역을_생성한다() {
        // when
        Ledgers newLedgers = Ledgers.recordApproval(initialAmount);

        // then
        assertThat(newLedgers.ledgers()).hasSize(1);
        assertThat(newLedgers.ledgers().getFirst().isApproved()).isTrue();
        assertThat(newLedgers.calculateBalanceAmount()).isEqualTo(initialAmount);
    }

    @Test
    @DisplayName("calculateBalanceAmount는 승인액에서 취소액을 뺀 금액을 반환한다")
    void calculateBalanceAmount_shouldReturnBalance() {
        // given: 50,000원 승인된 상태
        Ledgers ledgers = Ledgers.recordApproval(initialAmount);

        // when: 10,000원 취소 내역 추가
        Money partialCancelAmount = new Money(10000);
        ledgers.recordCancellation(partialCancelAmount, "부분 취소");

        // then: 잔액은 50000 - 10000 = 40000원
        assertThat(ledgers.calculateBalanceAmount().amount()).isEqualTo(40000);
        assertThat(ledgers.ledgers()).hasSize(2);
        assertThat(ledgers.ledgers().get(1).isCanceled()).isTrue();
        assertThat(ledgers.isFullyCancelled()).isFalse();
    }

    @Test
    void 취소_금액이_잔액을_초과할_경우_예외발생() {
        // given: 50,000원 승인된 상태
        Ledgers ledgers = Ledgers.recordApproval(initialAmount);

        // When & Then: 승인된 금액을 초과할 때, 예외 발생
        Money overCancelAmount = new Money(60000);
        assertThatThrownBy(() -> ledgers.recordCancellation(overCancelAmount, "전액 초과 시도"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("잔액이 0이면 isFullyCancelled는 참을 반환한다")
    void isFullyCancelled_shouldReturnTrueWhenBalanceIsZero() {
        // given
        Ledgers ledgers = Ledgers.recordApproval(initialAmount);

        // when
        ledgers.recordCancellation(initialAmount, "전액 취소");

        // Then
        assertThat(ledgers.isFullyCancelled()).isTrue();
    }
}