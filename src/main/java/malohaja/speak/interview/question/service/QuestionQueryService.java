package malohaja.speak.interview.question.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.entity.Question;
import malohaja.speak.interview.question.domain.request.QuestionSearchCondition;
import malohaja.speak.interview.question.domain.response.QuestionCardResponse;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


    public List<QuestionCardResponse> getByCondition(QuestionSearchCondition condition, PageRequest pageRequest) {
        Page<Question> questions = questionRepository.getByCondition(condition, pageRequest);
        return questions.stream()
                .map(question -> QuestionCardResponse.of(
                        question,
                        question.getAnswers().stream()
                                .limit(3)
                                .toList()))
                .toList();
    }

    public List<QuestionCardResponse> getMyBookmark(TokenInfo tokenInfo) {
        List<Question> questions = questionRepository.getMyBookmarkQuestion(tokenInfo.getId());
        return questions.stream()
                .map(question -> QuestionCardResponse.of(
                        question,
                        question.getAnswers().stream()
                                .limit(3)
                                .toList()))
                .toList();
    }
}
