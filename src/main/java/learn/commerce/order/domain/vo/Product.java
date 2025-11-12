package learn.commerce.order.domain.vo;

import java.util.UUID;
import learn.commerce.common.utils.UuidConverter;

public record Product(UUID id, String name) {
    public Product {
        if (id == null) {
            throw new IllegalArgumentException("상품 ID는 필수입니다");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수입니다");
        }
    }

    public static Product from(String id, String name) {
        return new Product(UuidConverter.fromString(id), name);
    }
}
