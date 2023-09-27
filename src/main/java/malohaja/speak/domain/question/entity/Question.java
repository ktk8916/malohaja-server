package malohaja.speak.domain.question.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.domain.BaseEntity;
import malohaja.speak.domain.member.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "question")
    private List<QuestionSkill> skills = new ArrayList<>();
    private String content;
}
