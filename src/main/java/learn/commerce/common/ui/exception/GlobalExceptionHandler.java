package learn.commerce.common.ui.exception;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String VALID_EXCEPTION_FORMAT = "필드명: %s, 예외 메세지: %s";

    private static String format(String field, String message) {
        return String.format(VALID_EXCEPTION_FORMAT, field, message);
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(Exception e) {
        log.error("{}: ", e.getClass().getSimpleName(), e);
        return ExceptionResponse.internal();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ExceptionResponse handleNoResourceFound(NoResourceFoundException e) {
        log.warn("{} : {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return ExceptionResponse.noResource();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errorSpots = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> format(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        log.warn("[{}] : {}", e.getClass().getSimpleName(), errorSpots, e);
        return ExceptionResponse.argumentNotValid(errorSpots);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionResponse handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        String customMessage = format(
                Optional.ofNullable(e.getRequiredType())
                        .map(Class::getSimpleName)
                        .orElse("Unknown"),
                " (으)로 변환할 수 없는 요청입니다."
        );
        log.warn("[{}] - {} : {}", e.getClass().getSimpleName(), e.getName(), customMessage, e);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, customMessage, null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse handleMissingRequestParameter(MissingServletRequestParameterException e) {
        String error = format(e.getParameterName(), e.getParameterType());
        log.warn("{} : {}", e.getClass().getSimpleName(), error, e);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, error, null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponse handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        log.warn("{} : {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return ExceptionResponse.notReadable();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponse handleBusinessException(IllegalArgumentException e) {
        log.error("{}: ", e.getMessage(), e);
        return new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage(), null);
    }
}
