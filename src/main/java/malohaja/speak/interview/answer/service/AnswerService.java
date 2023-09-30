package malohaja.speak.interview.answer.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.client.storage.StorageService;
import malohaja.speak.interview.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final StorageService storageService;
    private final AnswerRepository answerRepository;

    public String register(MultipartFile file){
        try {
            return storageService.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
