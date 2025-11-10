package learn.commerce.common.ui.exception;

import java.util.List;
import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        HttpStatus httpCode,
        String errors,
        List<String> details
) {

    public static ExceptionResponse internal() {
        return new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러 발생", null);
    }

    public static ExceptionResponse noResource() {
        return new ExceptionResponse(HttpStatus.NOT_FOUND, "존재하지 않는 리소스", null);
    }

    public static ExceptionResponse notReadable() {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, "응답 형식 불일치 (DTO 필드 등)", null);
    }

    public static ExceptionResponse argumentNotValid(List<String> errorSpots) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, "요청 유효성 검증 실패", errorSpots);
    }
}
