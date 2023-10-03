package malohaja.speak.interview.answer.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.interview.answer.service.AnswerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answer")
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping
    public String test(@RequestParam("file") MultipartFile file){
        return answerService.register(file);
    }
}
