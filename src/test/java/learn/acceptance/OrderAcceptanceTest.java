package learn.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;
import java.util.UUID;
import learn.acceptance.template.BaseAcceptanceTemplate;
import learn.acceptance.template.OrderRequestTemplate;
import learn.acceptance.template.PaymentRequestTemplate;
import learn.commerce.common.ui.ApiTemplate;
import learn.commerce.common.ui.exception.ExceptionResponse;
import learn.commerce.order.adapter.in.api.request.CancelOrderRequest;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.api.response.OrderItemResponse;
import learn.commerce.order.adapter.in.api.response.OrderResponse;
import learn.commerce.payment.adapter.in.api.dto.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class OrderAcceptanceTest extends BaseAcceptanceTemplate {

    @Autowired
    private OrderRequestTemplate template;

    @Autowired
    private PaymentRequestTemplate paymentTemplate;

    @Test
    void 사용자는_상품을_선택하여_주문을_생성한다() throws Exception {
        // given
        PurchaseOrderRequest 주문요청 = template.createRequest(1);

        // when
        OrderResponse response = template.postPurchaseOrder(주문요청);

        // then
        OrderResponse 조회된_주문 = template.getOrder(response.orderId());
        assertThat(조회된_주문.status()).isEqualTo("ORDER_COMPLETED");
    }

    @Test
    void 사용자가_주문을_전체_취소한다() throws Exception {
        // given
        PurchaseOrderRequest req = template.createRequest(1);
        OrderResponse res = template.postPurchaseOrder(req);
        String orderId = res.orderId();
        CancelOrderRequest 취소요청 = new CancelOrderRequest(List.of(), "단순 변심");
        paymentTemplate.postPaymentConfirm(new PaymentRequest("test_key", orderId, res.totalAmount()));

        // when
        ApiTemplate<Void> 응답 = template.postCancelOrder(orderId, 취소요청, new TypeReference<>() {
        });

        // then
        OrderResponse 조회된_주문 = template.getOrder(orderId);
        assertThat(응답.status()).isEqualTo("SUCCESS");
        assertThat(조회된_주문.status()).isEqualTo("ORDER_CANCELED");
    }

    @Test
    void 사용자가_주문을_부분_취소한다() throws Exception {
        // given
        PurchaseOrderRequest req = template.createRequest(3);
        OrderResponse res = template.postPurchaseOrder(req);
        UUID productId = UUID.fromString(res.items().getFirst().productId());
        CancelOrderRequest 취소요청 = new CancelOrderRequest(List.of(productId), "단순 변심");
        paymentTemplate.postPaymentConfirm(new PaymentRequest("test_key", res.orderId(), res.totalAmount()));

        // when
        ApiTemplate<Void> 응답 = template.postCancelOrder(res.orderId(), 취소요청, new TypeReference<>() {
        });

        // then
        OrderResponse 조회된_주문 = template.getOrder(res.orderId());
        List<OrderItemResponse> 조회된_아이템 = 조회된_주문.items();
        assertThat(응답.status()).isEqualTo("SUCCESS");
        assertThat(조회된_주문.status()).isEqualTo("PARTIAL_CANCELED");
        assertThat(조회된_아이템).extracting(OrderItemResponse::status)
                .containsExactly("CANCELED", "CONFIRMED", "CONFIRMED");
    }

    @Test
    void 이미_취소된_주문을_재취소_할_수_없다() throws Exception {
        // given
        PurchaseOrderRequest req = template.createRequest(3);
        OrderResponse res = template.postPurchaseOrder(req);
        UUID productId = UUID.fromString(res.items().getFirst().productId());
        CancelOrderRequest 취소요청 = new CancelOrderRequest(List.of(productId), "단순 변심");
        paymentTemplate.postPaymentConfirm(new PaymentRequest("test_key", res.orderId(), res.totalAmount()));
        template.postCancelOrder(res.orderId(), 취소요청, new TypeReference<>() {
        });

        // when
        ApiTemplate<ExceptionResponse> 응답 = template.postCancelOrder(res.orderId(), 취소요청, new TypeReference<>() {
        });

        // then
        assertThat(응답.status()).isEqualTo("ERROR");
        assertThat(응답.body().errors()).contains("이미", "주문", "취소된");
    }

    @Test
    void 사용자가_결제_완료된_주문을_구매_확정한다() throws Exception {
        // given
        PurchaseOrderRequest req = template.createRequest(3);
        OrderResponse res = template.postPurchaseOrder(req);
        paymentTemplate.postPaymentConfirm(new PaymentRequest("test_key", res.orderId(), res.totalAmount()));

        // when
        ApiTemplate<Void> 응답 = template.postConfirmOrder(UUID.fromString(res.orderId()), new TypeReference<>() {
        });

        // then
        OrderResponse 조회된_주문 = template.getOrder(res.orderId());
        assertThat(응답.status()).isEqualTo("SUCCESS");
        assertThat(조회된_주문.status()).isEqualTo("PURCHASE_CONFIRMED");
    }
}