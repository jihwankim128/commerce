package learn.commerce.order.domain.vo;

public record Money(int amount) {
    public static Money ZERO = new Money(0);

    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("금액은 0 이상이어야 합니다");
        }
    }

    public Money add(Money other) {
        try {
            int sum = Math.addExact(amount, other.amount);
            return new Money(sum);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("금액의 한도를 초과했습니다. (21억원 이상)");
        }
    }

    public Money multiply(int quantity) {
        try {
            int multiple = Math.multiplyExact(amount, quantity);
            return new Money(multiple);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("금액의 한도를 초과했습니다. (21억원 이상)");
        }
    }
}
