package malohaja.speak.interview.question.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.member.domain.entity.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;
    @ManyToOne
    private Member member;

    @Builder
    public QuestionLike(Question question, Member member) {
        this.question = question;
        this.member = member;
    }
}
