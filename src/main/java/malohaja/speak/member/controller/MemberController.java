package malohaja.speak.member.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.member.domain.request.SignupRequest;
import malohaja.speak.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(
            @AuthenticationPrincipal TokenInfo tokenInfo,
            @RequestBody SignupRequest signupRequest
    ){
        memberService.signup(tokenInfo, signupRequest);
    }
}
