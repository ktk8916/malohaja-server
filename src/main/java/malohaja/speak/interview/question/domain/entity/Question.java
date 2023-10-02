package malohaja.speak.interview.question.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.global.domain.BaseEntity;
import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.member.domain.entity.Member;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    private Set<SkillType> skills = new HashSet<>();
    private String content;
    private int likeCount;

    public void update(String content, Set<SkillType> skills){
        this.skills = skills;
        this.content = content;
    }

    @Builder
    public Question(Member member, Set<SkillType> skills, String content) {
        this.member = member;
        this.content = content;
        this.skills = skills;
    }
}
