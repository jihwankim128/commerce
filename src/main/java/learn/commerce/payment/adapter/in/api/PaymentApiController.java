package learn.commerce.payment.adapter.in.api;

import jakarta.validation.Valid;
import java.util.List;
import learn.commerce.payment.adapter.in.api.dto.request.PaymentRequest;
import learn.commerce.payment.application.port.in.ApprovePaymentUseCase;
import learn.commerce.payment.application.port.out.PaymentQueryPort;
import learn.commerce.payment.application.port.out.dto.PaymentQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentApiController {

    private final ApprovePaymentUseCase approvePaymentUseCase;
    private final PaymentQueryPort paymentQueryPort;

    @PostMapping("/confirm")
    public void confirmPayment(@Valid @RequestBody PaymentRequest request) {
        approvePaymentUseCase.approvePayment(request.toCommand());
    }

    @GetMapping
    public List<PaymentQueryDto> ledgers() {
        return paymentQueryPort.getPayments();
    }
}