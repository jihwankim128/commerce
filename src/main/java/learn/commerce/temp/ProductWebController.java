package learn.commerce.temp;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductWebController {
    @GetMapping
    public List<ProductResponse> getProducts() {
        return List.of(
                new ProductResponse(UUID.randomUUID().toString(), "우아한 티셔츠", 29000),
                new ProductResponse(UUID.randomUUID().toString(), "우아한 후드티", 49000),
                new ProductResponse(UUID.randomUUID().toString(), "토스 맨투맨", 39000),
                new ProductResponse(UUID.randomUUID().toString(), "토스 모자", 19000),
                new ProductResponse(UUID.randomUUID().toString(), "우아한 X 토스 Collaboration 신발", 150000)
        );
    }
}
