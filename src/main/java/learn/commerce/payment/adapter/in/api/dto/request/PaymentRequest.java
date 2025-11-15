package learn.commerce.payment.adapter.in.api.dto.request;

import jakarta.validation.constraints.NotNull;
import learn.commerce.payment.application.port.dto.command.PaymentApproval;

public record PaymentRequest(
        @NotNull String paymentKey,
        @NotNull String orderId,
        int amount
) {

    public PaymentApproval toCommand() {
        return new PaymentApproval(paymentKey, orderId, amount);
    }
}
