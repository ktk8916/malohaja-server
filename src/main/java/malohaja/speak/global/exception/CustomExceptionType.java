package malohaja.speak.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malohaja.speak.client.oauth2.exception.NoSupportedOauth2Exception;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionType {

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러", MalohajaException.class),
    NO_MATCHING_SKILL(HttpStatus.BAD_REQUEST, "일치하는 기술이 없습니다.", NoMatchingSkillException.class),
    NO_SUPPORTED_OAUTH2_(HttpStatus.BAD_REQUEST, "지원하지 않는 oauth2 입니다.",NoSupportedOauth2Exception .class),

    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final Class<? extends MalohajaException> type;

    public static CustomExceptionType of(Class<? extends MalohajaException> classType) {
        return Arrays.stream(values())
                .filter(exception -> exception.type.equals(classType))
                .findFirst()
                .orElse(UNKNOWN_EXCEPTION);
    }
}
