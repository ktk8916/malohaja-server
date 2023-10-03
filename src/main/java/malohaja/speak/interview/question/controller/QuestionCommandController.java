package malohaja.speak.interview.question.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.request.QuestionCreateRequest;
import malohaja.speak.interview.question.domain.request.QuestionUpdateRequest;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.domain.response.QuestionResponse;
import malohaja.speak.interview.question.service.QuestionCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionCommandController {

    private final QuestionCommandService questionCommandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse questionCreate(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @RequestBody QuestionCreateRequest request
    ){
        return questionCommandService.questionCreate(tokenInfo, request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionResponse questionUpdate(
            @PathVariable("id") Long id,
            @RequestBody QuestionUpdateRequest request
    ){
        TokenInfo tokenInfo = TokenInfo.builder().id(1L).build();
        return questionCommandService.questionUpdate(id, tokenInfo, request);
    }
}
