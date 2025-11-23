package learn.commerce.payment.adapter.out.toss;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.function.Supplier;
import learn.commerce.payment.adapter.out.toss.dto.TossPaymentApprovalDto;
import learn.commerce.payment.adapter.out.toss.dto.TossPaymentCancelDto;
import learn.commerce.payment.application.port.dto.command.PaymentApproval;
import learn.commerce.payment.application.port.dto.result.PaymentApprovalResult;
import learn.commerce.payment.application.port.out.PaymentGatewayPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class TossPaymentAdapter implements PaymentGatewayPort {

    private static final String TOSS_APPROVED_STATUS = "DONE";
    private final RestClient restClient;

    public TossPaymentAdapter(
            @Value("${toss.secret-key}") String secretKey,
            @Value("${toss.api-url}") String baseUrl
    ) {
        String encodedAuth = Base64.getEncoder()
                .encodeToString((secretKey + ":").getBytes(StandardCharsets.UTF_8));
        this.restClient = RestClient.builder()
                .baseUrl(URI.create(baseUrl))
                .defaultHeader("Authorization", "Basic " + encodedAuth)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public PaymentApprovalResult callApproval(PaymentApproval approval) {
        TossPaymentApprovalDto dto = TossPaymentApprovalDto.from(approval);
        return executeApiCall(() -> restClient.post()
                .uri("/confirm")
                .body(dto)
                .retrieve()
                .body(PaymentApprovalResult.class));
    }

    @Override
    public boolean validateApproval(String paymentStatus) {
        return TOSS_APPROVED_STATUS.equalsIgnoreCase(paymentStatus);
    }

    @Override
    public void cancelPayment(String paymentKey, int cancelAmount, String cancelReason) {
        TossPaymentCancelDto dto = new TossPaymentCancelDto(cancelReason, cancelAmount);
        executeApiCall(() -> {
            restClient.post()
                    .uri("/{paymentKey}/cancel", paymentKey)
                    .body(dto)
                    .retrieve()
                    .toBodilessEntity();
        });
    }

    private <T> T executeApiCall(Supplier<T> execution) {
        try {
            return execution.get();
        } catch (HttpClientErrorException e) {
            log.error("4xx Error 발생 log ==> {}", e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        } catch (HttpServerErrorException e) {
            log.error("5xx Error 발생 log ==> {}", e.getMessage(), e);
            throw new IllegalArgumentException("현재 페이 서비스에 문제가 발생했습니다.");
        }
    }

    private void executeApiCall(Runnable execution) {
        try {
            execution.run();
        } catch (HttpClientErrorException e) {
            log.error("4xx Error 발생 log ==> {}", e.getMessage(), e);
            throw new IllegalArgumentException(e.getMessage());
        } catch (HttpServerErrorException e) {
            log.error("5xx Error 발생 log ==> {}", e.getMessage(), e);
            throw new IllegalArgumentException("현재 페이 서비스에 문제가 발생했습니다.");
        }
    }
}
