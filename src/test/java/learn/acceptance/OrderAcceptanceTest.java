package learn.acceptance;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import learn.commerce.CommerceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(classes = CommerceApplication.class)
@AutoConfigureMockMvc
class OrderAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 사용자는_상품을_선택하여_주문을_생성할_수_있다() throws Exception {
        // given
        Map<String, Object> 주문요청 = getOrderRequest("김지환", "01012345678");

        // when - 주문 생성
        MvcResult 생성결과 = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(주문요청)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").exists())
                .andExpect(jsonPath("$.ordererName").value("김지환"))
                .andExpect(jsonPath("$.ordererPhoneNumber").value("01012345678"))
                .andExpect(jsonPath("$.totalAmount").value(65000))
                .andExpect(jsonPath("$.status").value("ORDER_COMPLETED"))
                .andExpect(jsonPath("$.items.length()").value(2))
                .andReturn();

        // then - 간단한 조회로 검증
        String 응답본문 = 생성결과.getResponse().getContentAsString();
        String 주문ID = JsonPath.read(응답본문, "$.orderId");
        mockMvc.perform(get("/api/orders/{orderId}", 주문ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalAmount").value(65000))
                .andExpect(jsonPath("$.status").value("ORDER_COMPLETED"))
                .andExpect(jsonPath("$.items[0].amount").value(30000))
                .andExpect(jsonPath("$.items[1].amount").value(35000));
    }

    private static Map<String, Object> getOrderRequest(String 구매자_이름, String 구매자_번호) {
        return Map.of(
                "ordererName", 구매자_이름,
                "ordererPhoneNumber", 구매자_번호,
                "items", getOrderItems()
        );
    }

    private static List<Map<String, ? extends Serializable>> getOrderItems() {
        return List.of(
                getOrderItem("우아한 티셔츠", 15000, 2),
                getOrderItem("토스 후드티", 35000, 1)
        );
    }

    private static Map<String, ? extends Serializable> getOrderItem(String 아이템명, int 가격, int 수량) {
        return Map.of(
                "productId", UUID.randomUUID().toString(),
                "productName", 아이템명,
                "price", 가격,
                "quantity", 수량
        );
    }
}