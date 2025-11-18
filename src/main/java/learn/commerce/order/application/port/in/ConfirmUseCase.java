package learn.commerce.order.application.port.in;

import java.util.UUID;

public interface ConfirmUseCase {

    void confirm(UUID orderId);
}
