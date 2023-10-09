package malohaja.speak.interview.question.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.global.domain.BaseEntity;
import malohaja.speak.interview.answer.domain.entity.Answer;
import malohaja.speak.member.domain.entity.Member;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<QuestionSkill> skills = new HashSet<>();
    private String content;
    private int likeCount;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
    @OneToMany(mappedBy = "question")
    private List<Bookmark> bookmarks;

    public void update(String content, Set<QuestionSkill> skills){
        this.skills.clear();
        this.skills.addAll(skills);
        this.content = content;
    }

    public void setSkills(Set<QuestionSkill> skills) {
        this.skills = skills;
    }

    @Builder
    public Question(Member member, Set<QuestionSkill> skills, String content) {
        this.member = member;
        this.content = content;
        this.skills = skills;
    }

    public static Question fromId(Long id){
        Question question = new Question();
        question.id = id;
        return question;
    }
}
