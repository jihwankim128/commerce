package learn.commerce.payment.adapter.out.event;

import learn.commerce.payment.adapter.out.event.dto.ApprovalEvent;
import learn.commerce.payment.application.port.out.PaymentEventPort;
import learn.commerce.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringPaymentEventAdapter implements PaymentEventPort {

    private final ApplicationEventPublisher publisher;

    @Override
    public void publishApproval(Payment payment) {
        ApprovalEvent event = ApprovalEvent.from(payment);
        publisher.publishEvent(event);
    }
}
