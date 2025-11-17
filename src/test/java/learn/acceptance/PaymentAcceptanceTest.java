package learn.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import learn.acceptance.template.BaseAcceptanceTemplate;
import learn.acceptance.template.OrderRequestTemplate;
import learn.acceptance.template.PaymentRequestTemplate;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.api.response.OrderResponse;
import learn.commerce.payment.adapter.in.api.dto.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class PaymentAcceptanceTest extends BaseAcceptanceTemplate {

    @Autowired
    private OrderRequestTemplate orderTemplate;

    @Autowired
    private PaymentRequestTemplate template;

    @Test
    void 클라이언트는_주문에_대해_결제를_승인할_수_있다() throws Exception {
        // given - 주문 생성 & 결제 승인 정보
        PurchaseOrderRequest req = orderTemplate.createRequest(1);
        OrderResponse res = orderTemplate.postPurchaseOrder(req);
        PaymentRequest 결제요청 = new PaymentRequest("test_payment_key_123", res.orderId(), res.totalAmount());

        // when
        template.postPaymentConfirm(결제요청);

        // then - 주문 상태 확인 (Policy: 주문 완료 처리)
        OrderResponse order = orderTemplate.getOrder(res.orderId());
        assertThat(order.status()).isEqualTo("PAYMENT_FULL_FILL");
    }
}