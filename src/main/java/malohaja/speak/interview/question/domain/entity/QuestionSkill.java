package malohaja.speak.interview.question.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.global.domain.skill.SkillType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionSkill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    @Enumerated(EnumType.STRING)
    private SkillType skill;

    @Builder
    public QuestionSkill(Question question, SkillType skill) {
        this.question = question;
        this.skill = skill;
    }
}
