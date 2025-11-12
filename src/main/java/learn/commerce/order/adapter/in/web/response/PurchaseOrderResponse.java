package learn.commerce.order.adapter.in.web.response;

import java.util.List;
import learn.commerce.order.application.port.in.result.PurchaseResult;

public record PurchaseOrderResponse(
        String orderId,
        String ordererName,
        String ordererPhoneNumber,
        int totalAmount,
        String status,
        List<PurchaseOrderItemResponse> items
) {
    public static PurchaseOrderResponse from(PurchaseResult result) {
        return new PurchaseOrderResponse(
                result.orderId(),
                result.ordererName(),
                result.ordererPhoneNumber(),
                result.totalAmount(),
                result.status(),
                result.items().stream()
                        .map(PurchaseOrderItemResponse::from)
                        .toList()
        );
    }
}