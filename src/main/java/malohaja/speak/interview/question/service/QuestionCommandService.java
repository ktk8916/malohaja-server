package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
