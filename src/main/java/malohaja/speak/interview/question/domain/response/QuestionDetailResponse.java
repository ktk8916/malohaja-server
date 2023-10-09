package malohaja.speak.interview.question.domain.response;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.answer.domain.dto.AnswerDto;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.entity.QuestionSkill;
import malohaja.speak.member.domain.dto.MemberDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record QuestionDetailResponse(
    Long id,
    Set<SkillType> skills,
    String content,
    int likeCount,
    LocalDateTime createdAt,
    MemberDto member
) {
    public static QuestionDetailResponse fromEntity(Question question){
        return new QuestionDetailResponse(
                question.getId(),
                question.getSkills().stream().map(QuestionSkill::getSkill).collect(Collectors.toSet()),
                question.getContent(),
                question.getLikeCount(),
                question.getCreatedAt(),
                MemberDto.fromEntity(question.getMember())
        );
    }
}
