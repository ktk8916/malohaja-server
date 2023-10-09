package malohaja.speak.interview.question.repository;

import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.request.QuestionSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomQuestionRepository {

    Question getByQuestionId(Long id);
    Page<Question> getByCondition(QuestionSearchCondition condition, Pageable pageable);
    List<Question> getMyBookmarkQuestion(Long memberId);
}
