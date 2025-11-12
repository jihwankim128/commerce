package learn.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import learn.commerce.CommerceApplication;
import learn.commerce.order.application.port.in.CreateOrderUseCase;
import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.command.PurchaseOrderItem;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CommerceApplication.class)
public class OrderUseCaseTest {

    @Autowired
    private CreateOrderUseCase createOrderUseCase;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 구매_주문_정보로_주문을_생성하면_주문_완료된_구매_결과가_반환된다() {
        // given
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem(UUID.randomUUID().toString(), "상품", 1000, 10);
        PurchaseOrder purchaseOrder = new PurchaseOrder("김지환", "01012345678", List.of(purchaseOrderItem));

        // when
        PurchaseResult result = createOrderUseCase.createOrder(purchaseOrder);

        // then
        OrderId orderId = new OrderId(UUID.fromString(result.orderId()));
        Order order = orderRepository.getByIdWithThrow(orderId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETED);
    }
}
