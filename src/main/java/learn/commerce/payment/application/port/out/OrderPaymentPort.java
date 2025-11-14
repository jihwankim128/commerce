package learn.commerce.payment.application.port.out;

public interface OrderPaymentPort {

    boolean isPayable(String orderId, int amount);

    void completePayment(String orderId, String paymentId);
}