package learn.commerce.order.adapter.in.web;

import learn.commerce.order.adapter.in.web.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.web.response.PurchaseOrderResponse;
import learn.commerce.order.application.port.in.CreateOrderUseCase;
import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;

    @PostMapping
    public PurchaseOrderResponse createOrder(@RequestBody PurchaseOrderRequest request) {
        PurchaseOrder command = request.toCommand();
        PurchaseResult result = createOrderUseCase.createOrder(command);
        return PurchaseOrderResponse.from(result);
    }
}
