package learn.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import learn.acceptance.template.BaseAcceptanceTemplate;
import learn.acceptance.template.OrderRequestTemplate;
import learn.acceptance.template.PaymentRequestTemplate;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderItemRequest;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.web.response.OrderResponse;
import learn.commerce.payment.adapter.in.web.dto.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PaymentAcceptanceTest extends BaseAcceptanceTemplate {

    @Autowired
    private OrderRequestTemplate orderRequestTemplate;

    @Autowired
    private PaymentRequestTemplate template;

    @Test
    void 클라이언트는_주문에_대해_결제를_승인할_수_있다() throws Exception {
        // given - 주문 생성 & 결제 승인 정보
        String orderId = 주문_생성();
        PaymentRequest 결제요청 = new PaymentRequest("test_payment_key_123", orderId, 30000);

        // when
        template.postPaymentConfirm(결제요청);

        // then - 주문 상태 확인 (Policy: 주문 완료 처리)
        OrderResponse order = orderRequestTemplate.getOrder(orderId);
        assertThat(order.status()).isEqualTo("PAYMENT_FULL_FILL");
    }

    private String 주문_생성() throws Exception {
        List<PurchaseOrderItemRequest> 주문_아이템 = List.of(
                orderRequestTemplate.createPurchaseItemRequest("우아한 테크 프리코스", 30000, 1)
        );
        PurchaseOrderRequest 주문요청 = orderRequestTemplate.createPurchaseRequest("김지환", "01012345678", 주문_아이템);

        return orderRequestTemplate.postPurchaseOrder(주문요청).orderId();
    }
}