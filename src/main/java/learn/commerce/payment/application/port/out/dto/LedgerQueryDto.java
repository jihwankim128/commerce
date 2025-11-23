package learn.commerce.payment.application.port.out.dto;

import java.time.LocalDateTime;
import java.util.List;
import learn.commerce.payment.domain.vo.Ledgers;

public record LedgerQueryDto(
        String type,
        int amount,
        String reason,
        LocalDateTime recordedAt
) {
    public static List<LedgerQueryDto> from(Ledgers ledgers) {
        return ledgers.ledgers().stream()
                .map(ledger -> new LedgerQueryDto(
                        ledger.getType().name(),
                        ledger.getAmount().amount(),
                        ledger.getReason(),
                        ledger.getRecordedAt()
                ))
                .toList();
    }
}
