package learn.commerce.order.application.service;

import java.util.UUID;
import learn.commerce.order.application.port.in.CancelOrderUseCase;
import learn.commerce.order.application.port.in.ConfirmUseCase;
import learn.commerce.order.application.port.in.CreateOrderUseCase;
import learn.commerce.order.application.port.in.OrderPayUseCase;
import learn.commerce.order.application.port.in.command.OrderCancellation;
import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import learn.commerce.order.application.port.out.CancelPaymentPort;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase, OrderPayUseCase, CancelOrderUseCase, ConfirmUseCase {

    private final CancelPaymentPort cancelPaymentPort;
    private final OrderRepository orderRepository;

    @Override
    public PurchaseResult createOrder(PurchaseOrder command) {
        Order order = command.toDomain();
        Order savedOrder = orderRepository.save(order);
        return PurchaseResult.from(savedOrder);
    }

    @Override
    public void completePaid(OrderId orderId, String paymentId) {
        Order order = orderRepository.getByIdWithThrow(orderId);
        order.completePaid(paymentId);
        orderRepository.save(order);
    }

    @Override
    public void cancel(OrderCancellation command) {
        Order order = orderRepository.getByIdWithThrow(command.toOrderId());
        order.cancel(command.productIds());

        cancelPaymentPort.cancelPayment(
                order.getPaymentId(),
                order.getItems().calculateCanceledAmount(),
                command.cancelReason()
        );
        orderRepository.save(order);
    }

    @Override
    public void confirm(UUID orderId) {
        Order order = orderRepository.getByIdWithThrow(new OrderId(orderId));
        order.confirm();
        orderRepository.save(order);
    }
}
