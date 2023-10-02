package malohaja.speak.interview.question.domain.response;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.member.domain.dto.MemberDto;

import java.util.Set;

public record QuestionDetailResponse(
    Long id,
    Set<SkillType> skills,
    String content,
    int likeCount,
    MemberDto member
) {
    public static QuestionDetailResponse fromEntity(Question question){
        return new QuestionDetailResponse(
                question.getId(),
                question.getSkills(),
                question.getContent(),
                question.getLikeCount(),
                MemberDto.fromEntity(question.getMember())
        );
    }
}
