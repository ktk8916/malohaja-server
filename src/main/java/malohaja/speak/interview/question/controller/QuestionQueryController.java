package malohaja.speak.interview.question.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.interview.question.domain.request.QuestionSearchCondition;
import malohaja.speak.interview.question.domain.response.QuestionCardResponse;
import malohaja.speak.interview.question.domain.response.QuestionDetailResponse;
import malohaja.speak.interview.question.service.QuestionQueryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/question")
public class QuestionQueryController {

    private final QuestionQueryService questionQueryService;

    @GetMapping("/{id}")
    public QuestionDetailResponse getById(@PathVariable("id") Long id){
        return questionQueryService.getById(id);
    }

    @GetMapping
    public List<QuestionCardResponse> getByCondition(
            QuestionSearchCondition condition,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "16") int size
    ){
        PageRequest pageRequest = PageRequest.of(page, size);
        return questionQueryService.getByCondition(condition, pageRequest);
    }

    @GetMapping("/bookmark/my")
    public List<QuestionCardResponse> getMyBookmark(@AuthenticationPrincipal TokenInfo tokenInfo){
        return questionQueryService.getMyBookmark(tokenInfo);
    }
}
