package malohaja.speak.auth.controller;

import lombok.RequiredArgsConstructor;
import malohaja.speak.auth.domain.response.AuthenticationResponse;
import malohaja.speak.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/{provider}")
    public AuthenticationResponse authenticate(
            @PathVariable("provider") String providerName,
            @RequestParam("code") String code
    ){
        return authService.authenticate(providerName, code);
    }
}
