package learn.commerce;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/sample")
    public Map<String, String> sample() {
        return Map.of("message", "Test");
    }
}
