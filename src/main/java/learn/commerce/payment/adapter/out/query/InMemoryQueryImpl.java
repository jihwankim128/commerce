package learn.commerce.payment.adapter.out.query;

import java.util.List;
import learn.commerce.payment.application.port.out.PaymentQueryPort;
import learn.commerce.payment.application.port.out.dto.PaymentQueryDto;
import learn.commerce.payment.domain.Payment;
import learn.commerce.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InMemoryQueryImpl implements PaymentQueryPort {

    private final PaymentRepository paymentRepository;

    @Override
    public List<PaymentQueryDto> getPayments() {
        List<Payment> payments = paymentRepository.getPayments();
        return payments.stream()
                .map(PaymentQueryDto::from)
                .toList();
    }
}
