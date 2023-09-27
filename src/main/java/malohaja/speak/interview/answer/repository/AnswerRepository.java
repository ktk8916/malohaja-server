package malohaja.speak.interview.answer.repository;

import malohaja.speak.interview.answer.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long>, CustomAnswerRepository {

}
