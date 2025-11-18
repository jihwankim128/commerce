package learn.commerce.payment.adapter.out.db;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Override
    public Payment getByIdWithThrow(PaymentId paymentId) {
        return Optional.ofNullable(store.get(paymentId))
                .orElseThrow(() -> new IllegalArgumentException("결제 정보를 찾을 수 없습니다."));
    }

    @Override
    public List<Payment> getPayments() {
        return store.values()
                .stream()
                .toList();
    }
}
