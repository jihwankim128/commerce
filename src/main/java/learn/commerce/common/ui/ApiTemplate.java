package learn.commerce.common.ui;

import java.time.LocalDateTime;

public record ApiTemplate<T>(
        String status,
        T body,
        LocalDateTime timestamp
) {
    private static final String SUCCESS_STATUS = "SUCCESS";

    public static <T> ApiTemplate<T> success(T body) {
        return new ApiTemplate<>(SUCCESS_STATUS, body, LocalDateTime.now());
    }

    public static <T> ApiTemplate<T> error(T body) {
        return new ApiTemplate<>("ERROR", body, LocalDateTime.now());
    }
}
