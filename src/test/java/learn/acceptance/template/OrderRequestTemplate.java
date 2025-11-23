package learn.acceptance.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.core.type.TypeReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import learn.commerce.common.ui.ApiTemplate;
import learn.commerce.order.adapter.in.api.request.CancelOrderRequest;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderItemRequest;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.api.response.OrderResponse;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@TestComponent
public class OrderRequestTemplate extends BaseRequestTemplate {

    private static final Random RANDOM = new Random();

    public PurchaseOrderRequest createRequest(int itemCount) {
        List<PurchaseOrderItemRequest> 주문_아이템 = new ArrayList<>();
        for (int i = 1; i <= itemCount; i++) {
            String productId = UUID.randomUUID().toString();
            String productName = i + "번 째 상품";
            int price = RANDOM.nextInt(10000, 100001) / 10000 * 10000;
            int quantity = RANDOM.nextInt(1, 11);
            주문_아이템.add(new PurchaseOrderItemRequest(productId, productName, price, quantity));
        }
        return new PurchaseOrderRequest("김지환", "01012345678", 주문_아이템);
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

    public OrderResponse getOrder(String orderId) throws Exception {
        MvcResult result = mockMvc.perform(get("/api/orders/{orderId}", orderId))
                .andDo(print())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andReturn();

        return extractBody(result, OrderResponse.class);
    }

    public <T> ApiTemplate<T> postCancelOrder(String orderId, CancelOrderRequest request,
                                              TypeReference<ApiTemplate<T>> ref) throws Exception {
        MvcResult result = mockMvc.perform(
                        post("/api/orders/{orderId}/cancel", orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andReturn();
        return extractResponse(result, ref);
    }

    public <T> ApiTemplate<T> postConfirmOrder(UUID orderId, TypeReference<ApiTemplate<T>> ref) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/orders/{orderId}/confirm", orderId))
                .andDo(print())
                .andReturn();
        return extractResponse(result, ref);
    }
}
