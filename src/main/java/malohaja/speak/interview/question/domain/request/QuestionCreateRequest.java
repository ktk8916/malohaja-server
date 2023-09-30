package malohaja.speak.interview.question.domain.request;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.member.domain.entity.Member;

import java.util.List;

public record QuestionCreateRequest(
        String content,
        List<String> skills
) {

    public Question toEntity(Long memberId){
        return Question.builder()
                .content(content)
                .member(Member.fromId(memberId))
                .skills(SkillType.convertList(skills))
                .build();
    }
}
