package learn.commerce.payment.adapter.out.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import learn.commerce.payment.domain.Payment;
import learn.commerce.payment.domain.PaymentRepository;
import learn.commerce.payment.domain.vo.PaymentId;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<PaymentId, Payment> store = new ConcurrentHashMap<>();

    @Override
    public Payment save(Payment payment) {
        return store.put(payment.getPaymentId(), payment);
    }
}
