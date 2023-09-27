package malohaja.speak.interview.answer.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import malohaja.speak.interview.answer.domain.entity.Answer;
import malohaja.speak.interview.answer.domain.entity.QAnswer;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static malohaja.speak.interview.answer.domain.entity.QAnswer.answer;

public class CustomAnswerRepositoryImpl implements CustomAnswerRepository{

    private final JPAQueryFactory queryFactory;

    public CustomAnswerRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Answer getById(Long id){
        Answer findAnswer = queryFactory
                .selectFrom(answer)
                .where(answer.id.eq(id))
                .fetchOne();

        if(findAnswer==null){
            throw new NoSuchElementException("not found");
        }

        return findAnswer;
    }
}
