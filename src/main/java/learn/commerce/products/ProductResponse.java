package learn.commerce.products;

public record ProductResponse(
        String productId,
        String productName,
        int price
) {
}