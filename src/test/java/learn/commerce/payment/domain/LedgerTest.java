package learn.commerce.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import learn.commerce.common.domain.Money;
import org.junit.jupiter.api.Test;

class LedgerTest {

    private final LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 10, 0);

    @Test
    void 결제_승인_장부가_발행된다() {
        // when
        Ledger ledger = Ledger.approve(new Money(1000), fixedTime);

        // then
        assertThat(ledger.getAmount().amount()).isEqualTo(1000);
        assertThat(ledger.isApproved()).isTrue();
        assertThat(ledger.getRecordedAt()).isEqualTo(fixedTime);
    }

    @Test
    void 결제_취소_장부가_발행된다() {
        // when
        Ledger ledger = Ledger.cancel(new Money(1000), "취소", fixedTime);

        // then
        assertThat(ledger.getAmount().amount()).isEqualTo(1000);
        assertThat(ledger.isCanceled()).isTrue();
        assertThat(ledger.getReason()).isEqualTo("취소");
    }
}