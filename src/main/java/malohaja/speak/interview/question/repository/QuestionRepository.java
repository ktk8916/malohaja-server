package malohaja.speak.interview.question.repository;

import malohaja.speak.interview.question.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long>, CustomQuestionRepository {
}
