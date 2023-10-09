package malohaja.speak.interview.question.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.request.QuestionSearchCondition;
import malohaja.speak.interview.question.exception.QuestionNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static malohaja.speak.interview.answer.domain.entity.QAnswer.answer;
import static malohaja.speak.interview.question.domain.entity.QQuestion.question;
import static malohaja.speak.interview.question.domain.entity.QQuestionSkill.questionSkill;

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

    @Override
    public Page<Question> getByCondition(QuestionSearchCondition condition, Pageable pageable) {
        List<Question> questions = queryFactory
                .selectFrom(question)
                .distinct()
                .leftJoin(question.skills, questionSkill)
                .leftJoin(question.answers, answer)
                .fetchJoin()
                .where(
                        contentContains(condition.keyword()),
                        skillContains(condition.skills()),
                        isDeletedFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(question.count())
                .distinct()
                .from(question)
                .leftJoin(question.skills, questionSkill)
                .where(
                        contentContains(condition.keyword()),
                        skillContains(condition.skills()),
                        isDeletedFalse()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchOne();

        return new PageImpl<>(questions, pageable, totalCount);
    }

    private BooleanExpression skillContains(List<String> skills) {
        return skills==null || skills.isEmpty() ?
                null :
                questionSkill.skill.in(SkillType.convertList(skills));
    }

    private BooleanExpression isDeletedFalse() {
        return question.isDeleted.isFalse();
    }

    private BooleanExpression contentContains(String keyword) {
        return keyword == null ?
                null :
                question.content.contains(keyword);
    }
}
