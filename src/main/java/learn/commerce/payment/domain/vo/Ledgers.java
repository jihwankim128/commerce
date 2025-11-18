package learn.commerce.payment.domain.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import learn.commerce.common.domain.Money;
import learn.commerce.payment.domain.Ledger;

public record Ledgers(List<Ledger> ledgers) {
    public static Ledgers recordApproval(Money amount) {
        List<Ledger> ledgers = new ArrayList<>();
        ledgers.add(Ledger.approve(amount, LocalDateTime.now()));
        return new Ledgers(ledgers);
    }

    public void recordCancellation(Money amount, String reason) {
        if (calculateBalanceAmount().isLessThan(amount)) {
            throw new IllegalArgumentException("취소 가능 금액 초과");
        }
        ledgers.add(Ledger.cancel(amount, reason, LocalDateTime.now()));
    }

    public Money calculateBalanceAmount() {
        Money approved = calculatedApprovedAmount();
        Money cancelled = calculatedCanceledAmount();
        return approved.subtract(cancelled);
    }

    public boolean isFullyCancelled() {
        return calculateBalanceAmount().equals(Money.ZERO);
    }

    private Money calculatedCanceledAmount() {
        return ledgers.stream()
                .filter(Ledger::isCanceled)
                .map(Ledger::getAmount)
                .reduce(Money.ZERO, Money::add);
    }

    private Money calculatedApprovedAmount() {
        return ledgers.stream()
                .filter(Ledger::isApproved)
                .map(Ledger::getAmount)
                .reduce(Money.ZERO, Money::add);
    }
}