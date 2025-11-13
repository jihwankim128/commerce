package learn.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import learn.acceptance.template.BaseAcceptanceTemplate;
import learn.acceptance.template.OrderRequestTemplate;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderItemRequest;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.web.response.OrderResponse;
import learn.commerce.order.domain.vo.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

class PaymentAcceptanceTest extends BaseAcceptanceTemplate {

    @Autowired
    private OrderRequestTemplate orderRequestTemplate;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 클라이언트는_주문에_대해_결제를_승인할_수_있다() throws Exception {
        // given - 주문 생성 & 결제 승인 정보
        String orderId = 주문_생성();
        Map<String, Object> 결제요청 = Map.of(
                "paymentKey", "test_payment_key_123",
                "orderId", orderId,
                "amount", 30000,
                "method", "CARD"
        );

        // when
        MvcResult 결제결과 = mockMvc.perform(post("/api/payments/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(결제요청)))
                .andDo(print())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.body.paymentKey").value("test_payment_key_123"))
                .andExpect(jsonPath("$.body.orderId").value(orderId))
                .andExpect(jsonPath("$.body.amount").value(30000))
                .andExpect(jsonPath("$.body.status").value("DONE"))
                .andReturn();

        // then - 주문 상태 확인 (Policy: 주문 완료 처리)
        OrderResponse order = orderRequestTemplate.getOrder(orderId);
        assertThat(order.status()).isEqualTo(OrderStatus.PAYMENT_FULL_FILL.name());
    }

    private String 주문_생성() throws Exception {
        List<PurchaseOrderItemRequest> 주문_아이템 = List.of(
                orderRequestTemplate.createPurchaseItemRequest("우아한 테크 프리코스", 30000, 1)
        );
        PurchaseOrderRequest 주문요청 = orderRequestTemplate.createPurchaseRequest("김지환", "01012345678", 주문_아이템);

        return orderRequestTemplate.postPurchaseOrder(주문요청).orderId();
    }
}