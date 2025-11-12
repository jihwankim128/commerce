package learn.commerce.order.domain.vo;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public record Orderer(String name, String phoneNumber) {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^010\\d{8}$");
    private static final Predicate<String> IS_VALID_PHONE = PHONE_NUMBER_PATTERN.asMatchPredicate();

    public Orderer {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("주문자 이름은 필수입니다");
        }
        if (phoneNumber == null || !IS_VALID_PHONE.test(phoneNumber)) {
            throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다");
        }
    }
}
