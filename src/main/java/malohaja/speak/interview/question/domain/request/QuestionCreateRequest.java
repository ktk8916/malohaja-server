package malohaja.speak.interview.question.domain.request;

import java.util.List;

public record QuestionCreateRequest(
        String content,
        List<String> skills
) {

}
