package learn.commerce.order.adapter.in.api;

import java.util.List;
import learn.commerce.order.adapter.in.api.request.PurchaseOrderRequest;
import learn.commerce.order.adapter.in.api.response.OrderResponse;
import learn.commerce.order.application.port.in.CreateOrderUseCase;
import learn.commerce.order.application.port.in.command.PurchaseOrder;
import learn.commerce.order.application.port.in.result.PurchaseResult;
import learn.commerce.order.application.port.out.OrderQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderApiController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderQueryPort orderQueryPort;

    @PostMapping
    public OrderResponse createOrder(@RequestBody PurchaseOrderRequest request) {
        PurchaseOrder command = request.toCommand();
        PurchaseResult result = createOrderUseCase.createOrder(command);
        return OrderResponse.from(result);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable String orderId) {
        return orderQueryPort.getOrder(orderId);
    }

    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderQueryPort.getOrders();
    }
}
