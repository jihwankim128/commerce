package learn.commerce.order.adapter.in.event;

import learn.commerce.order.application.port.in.UpdateOrderUseCase;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.payment.adapter.out.event.dto.ApprovalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventListener {

    private final UpdateOrderUseCase updateOrderUseCase;

    @Async
    @EventListener
    public void completePayment(ApprovalEvent event) {
        updateOrderUseCase.complete(OrderId.from(event.orderId()), event.paymentId());
    }
}
