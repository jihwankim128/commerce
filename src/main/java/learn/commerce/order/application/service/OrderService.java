package learn.commerce.order.application.service;

import learn.commerce.order.application.port.in.CreateOrderUseCase;
import learn.commerce.order.application.port.in.UpdateOrderUseCase;
import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService implements CreateOrderUseCase, UpdateOrderUseCase {

    private final OrderRepository orderRepository;

    @Override
    public PurchaseResult createOrder(PurchaseOrder command) {
        Order order = command.toDomain();
        Order savedOrder = orderRepository.save(order);
        return PurchaseResult.from(savedOrder);
    }

    @Override
    public void complete(OrderId orderId, String paymentId) {
        Order order = orderRepository.getByIdWithThrow(orderId);
        order.complete(paymentId);
        orderRepository.save(order);
    }
}
