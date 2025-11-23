package learn.commerce.payment.application.port.out;

import java.util.List;
import learn.commerce.payment.application.port.out.dto.PaymentQueryDto;

public interface PaymentQueryPort {

    List<PaymentQueryDto> getPayments();
}
