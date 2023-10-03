package malohaja.speak.interview.answer.domain.dto;

import malohaja.speak.interview.answer.domain.entity.Answer;
import malohaja.speak.member.domain.dto.MemberDto;

public record AnswerDto(
        Long id,
        MemberDto member,
        String voiceUri
) {
    public static AnswerDto fromEntity(Answer answer){
        return new AnswerDto(
                answer.getId(),
                MemberDto.fromEntity(answer.getMember()),
                answer.getVoiceUri()
        );
    }
}
