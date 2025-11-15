package learn.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import learn.acceptance.template.BaseAcceptanceTemplate;
import learn.acceptance.template.OrderRequestTemplate;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderItemRequest;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.api.response.OrderResponse;
import learn.commerce.order.domain.Order;
import learn.commerce.order.domain.OrderRepository;
import learn.commerce.order.domain.vo.OrderId;
import learn.commerce.order.domain.vo.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OrderAcceptanceTest extends BaseAcceptanceTemplate {

    @Autowired
    private OrderRequestTemplate requestTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void 사용자는_상품을_선택하여_주문을_생성한다() throws Exception {
        // given
        List<PurchaseOrderItemRequest> 주문_아이템 = List.of(
                requestTemplate.createPurchaseItemRequest("우아한 티셔츠", 15000, 2),
                requestTemplate.createPurchaseItemRequest("토스 후드티", 35000, 1)
        );
        PurchaseOrderRequest 주문요청 = requestTemplate.createPurchaseRequest("김지환", "01012345678", 주문_아이템);

        // when
        OrderResponse response = requestTemplate.postPurchaseOrder(주문요청);

        // then
        OrderId orderId = new OrderId(UUID.fromString(response.orderId()));
        Order order = orderRepository.getByIdWithThrow(orderId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.ORDER_COMPLETED);
    }
}