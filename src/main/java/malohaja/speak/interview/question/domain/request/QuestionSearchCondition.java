package malohaja.speak.interview.question.domain.request;

import java.util.List;

public record QuestionSearchCondition(
        List<String> skills,
        String sort,
        String keyword
) {
}
