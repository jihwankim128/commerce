package learn.commerce.order.domain.vo;

import learn.commerce.common.domain.Money;

public record OrderItem(Product product, Money price, int quantity, Money totalAmount) {
    public OrderItem {
        if (product == null || price == null) {
            throw new IllegalArgumentException("주문 상품은 필수 정보입니다.");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("상품 수량은 1개 이상이어야 합니다");
        }
        totalAmount = price.multiply(quantity);
    }

    public static OrderItem of(Product product, Money price, int quantity) {
        return new OrderItem(product, price, quantity, null);
    }
}
