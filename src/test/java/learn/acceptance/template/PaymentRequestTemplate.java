package learn.acceptance.template;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import learn.commerce.payment.adapter.in.api.dto.request.PaymentRequest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.http.MediaType;

@TestComponent
public class PaymentRequestTemplate extends BaseRequestTemplate {

    public void postPaymentConfirm(PaymentRequest 결제요청) throws Exception {
        mockMvc.perform(post("/api/payments/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(결제요청)))
                .andDo(document("payment-confirm", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }
}
