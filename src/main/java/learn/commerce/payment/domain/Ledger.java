package learn.commerce.payment.domain;

import java.time.LocalDateTime;
import learn.commerce.common.domain.Money;
import learn.commerce.payment.domain.vo.LedgerType;
import lombok.Getter;

@Getter
public class Ledger {
    private final Money amount;
    private final LedgerType type;
    private final String reason;
    private final LocalDateTime recordedAt;

    private Ledger(Money amount, LedgerType type, String reason, LocalDateTime recordedAt) {
        this.amount = amount;
        this.type = type;
        this.reason = reason;
        this.recordedAt = recordedAt;
    }

    public static Ledger approve(Money amount, LocalDateTime recordedAt) {
        return new Ledger(amount, LedgerType.APPROVE, "결제 승인", recordedAt);
    }

    public static Ledger cancel(Money amount, String reason, LocalDateTime recordedAt) {
        return new Ledger(amount, LedgerType.CANCEL, reason, recordedAt);
    }

    public boolean isCanceled() {
        return type == LedgerType.CANCEL;
    }

    public boolean isApproved() {
        return type == LedgerType.APPROVE;
    }
}