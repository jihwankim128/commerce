package learn.commerce.payment.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentWebController {

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }

    @GetMapping("/ledger")
    public String ledger() {
        return "ledger";
    }
}
