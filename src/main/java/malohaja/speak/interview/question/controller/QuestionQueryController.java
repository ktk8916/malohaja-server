package malohaja.speak.interview.question.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.service.QuestionQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionQueryController {

    private final QuestionQueryService questionQueryService;

    @GetMapping("/{id}")
    public QuestionDetailResponse getById(@PathVariable("id") Long id){
        return questionQueryService.getById(id);
    }

}
