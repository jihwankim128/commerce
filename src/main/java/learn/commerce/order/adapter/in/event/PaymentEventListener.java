package learn.commerce.order.adapter.in.event;

import learn.commerce.order.application.port.in.OrderPayUseCase;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.payment.adapter.out.event.dto.ApprovalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final OrderPayUseCase orderPayUseCase;

    @Async
    @EventListener
    public void completePayment(ApprovalEvent event) {
        orderPayUseCase.completePaid(OrderId.from(event.orderId()), event.paymentId());
    }
}
