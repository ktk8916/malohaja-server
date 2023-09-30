package malohaja.speak.global.exception;

import org.springframework.http.HttpStatus;

public class MalohajaException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public MalohajaException() {
        CustomExceptionType customExceptionType = CustomExceptionType.of(this.getClass());
        this.message = customExceptionType.getMessage();
        this.httpStatus = customExceptionType.getHttpStatus();
    }
}
