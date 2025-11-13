package learn.acceptance.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;
import java.util.UUID;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderItemRequest;
import learn.commerce.order.adapter.in.web.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.web.response.OrderResponse;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@TestComponent
public class OrderRequestTemplate extends BaseRequestTemplate {

    public PurchaseOrderRequest createPurchaseRequest(String 구매자, String 연락처, List<PurchaseOrderItemRequest> 아이템) {
        return new PurchaseOrderRequest(구매자, 연락처, 아이템);
    }

    public PurchaseOrderItemRequest createPurchaseItemRequest(String 상품명, int 가격, int 수량) {
        return new PurchaseOrderItemRequest(UUID.randomUUID().toString(), 상품명, 가격, 수량);
    }

    public OrderResponse postPurchaseOrder(PurchaseOrderRequest 주문요청) throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/api/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(주문요청))
                )
                .andDo(print())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andReturn();

        return extractBody(result, OrderResponse.class);
    }
}
