package learn.commerce.temp;

public record ProductResponse(
        String productId,
        String productName,
        int price
) {
}