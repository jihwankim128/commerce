package learn.commerce.common.utils;

import java.util.UUID;

public class UuidConverter {

    private UuidConverter() {
    }

    public static UUID fromString(String uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException("UUID 정보가 비어있습니다. (NullPointer)");
        }
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("UUID 형식이 올바르지 않습니다.", e);
        }
    }
}
