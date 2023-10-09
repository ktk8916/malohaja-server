package malohaja.speak.interview.question.domain.response;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.entity.QuestionSkill;

import java.util.Set;
import java.util.stream.Collectors;

public record QuestionResponse(
        Long id,
        Set<SkillType> skills,
        String content,
        int likeCount,
        Long memberId
) {
    public static QuestionResponse fromEntity(Question question){
        return new QuestionResponse(
                question.getId(),
                question.getSkills().stream().map(QuestionSkill::getSkill).collect(Collectors.toSet()),
                question.getContent(),
                question.getLikeCount(),
                question.getMember().getId()
        );
    }
}
