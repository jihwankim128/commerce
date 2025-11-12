package learn.commerce.common.utils;

import java.util.UUID;

public class UuidConverter {

    private UuidConverter() {
    }

    public static UUID fromString(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID 형식이 올바르지 않습니다.", e);
        }
    }
}
