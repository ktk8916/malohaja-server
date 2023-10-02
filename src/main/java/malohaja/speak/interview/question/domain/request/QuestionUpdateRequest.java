package malohaja.speak.interview.question.domain.request;

import java.util.List;

public record QuestionUpdateRequest(
        String content,
        List<String> skills) {
}
