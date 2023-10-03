package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.response.QuestionCardResponse;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {

    private final QuestionRepository questionRepository;

    public QuestionDetailResponse getById(Long id){
        Question question = questionRepository.getByQuestionId(id);
        return QuestionDetailResponse.fromEntity(question);
    }

    // 나중에 바꾸자
    public List<QuestionCardResponse> getByCondition() {
        List<Question> questions = questionRepository.findAll();
        return questions.stream()
                .map(question -> QuestionCardResponse.of(
                        question,
                        question.getAnswers().stream()
                                .limit(3)
                                .toList()
                )).toList();
    }
}
