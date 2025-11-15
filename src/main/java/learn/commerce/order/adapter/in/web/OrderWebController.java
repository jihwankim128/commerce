package learn.commerce.order.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderWebController {

    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable String orderId) {
        return "order-detail";
    }

    @GetMapping
    public String orders() {
        return "orders";
    }
}
