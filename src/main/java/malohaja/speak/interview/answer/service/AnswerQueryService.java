package malohaja.speak.interview.answer.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.interview.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerQueryService {

    private final AnswerRepository answerRepository;

}
