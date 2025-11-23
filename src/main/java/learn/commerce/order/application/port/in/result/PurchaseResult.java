package learn.commerce.order.application.port.in.result;

import java.util.List;
import learn.commerce.order.domain.Order;

public record PurchaseResult(
        String orderId,
        String ordererName,
        String ordererPhoneNumber,
        int totalAmount,
        String status,
        List<PurchaseItemResult> items
) {
    public static PurchaseResult from(Order order) {
        return new PurchaseResult(
                order.getOrderId().toString(),
                order.getOrderer().name(),
                order.getOrderer().phoneNumber(),
                order.getTotalAmount().amount(),
                order.getStatus().name(),
                order.getItems().items().stream()
                        .map(PurchaseItemResult::from)
                        .toList()
        );
    }
}
