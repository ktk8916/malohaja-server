package malohaja.speak.interview.question.domain.response;

import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.interview.answer.domain.dto.AnswerDto;
import malohaja.speak.interview.answer.domain.entity.Answer;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.member.domain.dto.MemberDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record QuestionCardResponse(
        String content,
        Set<SkillType> skills,
        MemberDto member,
        LocalDateTime createdAt,
        List<AnswerDto> bestAnswers
) {
    public static QuestionCardResponse of(Question question, List<Answer> bestAnswers){
        return new QuestionCardResponse(
                question.getContent(),
                question.getSkills(),
                MemberDto.fromEntity(question.getMember()),
                question.getCreatedAt(),
                bestAnswers.stream().map(AnswerDto::fromEntity).toList()
        );
    }
}
