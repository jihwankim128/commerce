package learn.commerce.order.adapter.in.api.request;

import java.util.List;
import learn.commerce.order.application.port.in.command.PurchaseOrder;

public record PurchaseOrderRequest(
        String ordererName,
        String ordererPhoneNumber,
        List<PurchaseOrderItemRequest> items
) {
    public PurchaseOrder toCommand() {
        return new PurchaseOrder(
                ordererName,
                ordererPhoneNumber,
                items.stream()
                        .map(PurchaseOrderItemRequest::toCommand)
                        .toList()
        );
    }
}