package malohaja.speak.interview.question.repository;

import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.exception.QuestionNotFoundException;
import malohaja.speak.member.domain.entity.Member;
import malohaja.speak.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("질문 번호로 질문을 찾는다.")
    @Test
    void getByQuestionId(){
        // given
        Member member = Member.builder()
                .nickName("무지")
                .build();

        Member savedMember = memberRepository.save(member);

        Question question = Question.builder()
                .member(member)
                .content("질문입니다.")
                .build();

        Question saved = questionRepository.save(question);

        // when
        Question findQuestion = questionRepository.getByQuestionId(saved.getId());

        // then
        assertThat(findQuestion.getMember()).isEqualTo(savedMember);
        assertThat(findQuestion.getContent()).isEqualTo("질문입니다.");
    }

    @DisplayName("없는 번호로 질문을 찾을 시, 예외가 발생한다.")
    @Test
    void getByInvalidQuestionId(){
        // given
        Member member = Member.builder()
                .nickName("무지")
                .build();

        Member savedMember = memberRepository.save(member);

        Question question = Question.builder()
                .member(member)
                .content("질문입니다.")
                .build();

        Question saved = questionRepository.save(question);

        // when, then
        assertThatThrownBy(()->questionRepository.getByQuestionId(9999L))
                .isInstanceOf(QuestionNotFoundException.class);
    }

}