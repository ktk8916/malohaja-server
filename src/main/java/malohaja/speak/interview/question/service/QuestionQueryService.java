package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {

    private final QuestionRepository questionRepository;

    public QuestionDetailResponse getById(Long id){
        Question question = questionRepository.getByQuestionId(id);
        return QuestionDetailResponse.fromEntity(question);
    }
}
