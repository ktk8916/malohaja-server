package malohaja.speak.interview.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import malohaja.speak.interview.answer.domain.entity.QAnswer;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.exception.QuestionNotFoundException;

import static malohaja.speak.interview.answer.domain.entity.QAnswer.answer;
import static malohaja.speak.interview.question.domain.entity.QQuestion.question;

public class CustomQuestionRepositoryImpl implements CustomQuestionRepository{

    private final JPAQueryFactory queryFactory;

    public CustomQuestionRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /*
    command 와 query 에서 모두 사용하니
    이 메서드만 무조건 Question 을 반환하도록 custom
     */
    @Override
    public Question getByQuestionId(Long id){
        Question findQuestion = queryFactory
                .selectFrom(question)
                .where(question.id.eq(id))
                .fetchOne();

        if(findQuestion==null){
            throw new QuestionNotFoundException();
        }

        return findQuestion;
    }

}
