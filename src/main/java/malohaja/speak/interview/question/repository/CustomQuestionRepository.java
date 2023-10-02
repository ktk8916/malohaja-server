package malohaja.speak.interview.question.repository;

import malohaja.speak.interview.question.domain.entity.Question;

public interface CustomQuestionRepository {

    Question getByQuestionId(Long id);
}
