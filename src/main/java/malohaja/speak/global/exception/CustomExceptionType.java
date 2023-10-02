package malohaja.speak.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malohaja.speak.client.oauth2.exception.NoSupportedOauth2Exception;
import malohaja.speak.interview.question.exception.InvalidQuestionWriterException;
import malohaja.speak.member.exception.MemberNotFoundException;
import malohaja.speak.member.exception.NoMatchingCareerException;
import malohaja.speak.member.exception.NoMatchingJobException;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CustomExceptionType {

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러", MalohajaException.class),
    NO_MATCHING_SKILL(HttpStatus.BAD_REQUEST, "일치하는 기술이 없습니다.", NoMatchingSkillException.class),
    NO_MATCHING_JOB(HttpStatus.BAD_REQUEST, "일치하는 직무가 없습니다.", NoMatchingJobException.class),
    NO_MATCHING_CAREER(HttpStatus.BAD_REQUEST, "일치하는 경력이 없습니다.", NoMatchingCareerException.class),
    NO_SUPPORTED_OAUTH2(HttpStatus.BAD_REQUEST, "지원하지 않는 oauth2 입니다.",NoSupportedOauth2Exception .class),
    INVALID_QUESTION_WRITER(HttpStatus.UNAUTHORIZED, "질문 작성자가 아닙니다.", InvalidQuestionWriterException.class),
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다.", MemberNotFoundException.class),

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
