package learn.commerce.payment.application.port.out.dto;

import java.util.List;
import java.util.UUID;
import learn.commerce.payment.domain.Payment;

public record PaymentQueryDto(
        UUID id,
        UUID orderId,
        int approvedAmount,
        int canceledAmount,
        int balanceAmount,
        String method,
        String status,
        List<LedgerQueryDto> ledgers
) {

    public static PaymentQueryDto from(Payment payment) {
        return new PaymentQueryDto(
                payment.getPaymentId().value(),
                payment.getOrderId(),
                payment.getLedgers().calculatedApprovedAmount().amount(),
                payment.getLedgers().calculatedCanceledAmount().amount(),
                payment.getLedgers().calculateBalanceAmount().amount(),
                payment.getMethod().getValue(),
                payment.getStatus().name(),
                LedgerQueryDto.from(payment.getLedgers())
        );
    }
}
