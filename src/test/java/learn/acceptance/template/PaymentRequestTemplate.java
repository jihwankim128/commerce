package learn.acceptance.template;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import learn.commerce.payment.adapter.in.web.dto.request.PaymentRequest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;

@TestComponent
public class PaymentRequestTemplate extends BaseRequestTemplate {

    public void postPaymentConfirm(PaymentRequest 결제요청) throws Exception {
        mockMvc.perform(post("/api/payments/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(결제요청)))
                .andDo(print())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}
