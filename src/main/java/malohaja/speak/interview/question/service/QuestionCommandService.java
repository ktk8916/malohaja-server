package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.request.QuestionUpdateRequest;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.exception.InvalidQuestionWriterException;
import malohaja.speak.interview.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCommandService {

    private final QuestionRepository questionRepository;

    public QuestionResponse questionCreate(TokenInfo tokenInfo, QuestionCreateRequest request){
        Question question = request.toEntity(tokenInfo.getId());
        Question savedQuestion = questionRepository.save(question);

        return QuestionResponse.fromEntity(savedQuestion);
    }
    public QuestionResponse questionUpdate(Long id, TokenInfo tokenInfo, QuestionUpdateRequest request){
        Question question = questionRepository.getByQuestionId(id);

        if(!isValidMember(question, tokenInfo.getId())){
            throw new InvalidQuestionWriterException();
        }

        question.update(request.content(), SkillType.convertList(request.skills()));
        return QuestionResponse.fromEntity(question);
    }

    private boolean isValidMember(Question question, Long memberId){
        return Objects.equals(question.getMember().getId(), memberId);
    }
}
