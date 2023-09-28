package malohaja.speak.auth.domain.response;

/*
우선 access token만 사용하도록 빠르게 구현하고
후에 refresh token을 도입하자
 */
public record AuthenticationResponse(
        String redirect,
        String accessToken
) {
    public static AuthenticationResponse of(String redirect, String accessToken){
        return new AuthenticationResponse(
                redirect,
                accessToken
        );
    }
}
