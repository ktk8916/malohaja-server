package malohaja.speak.auth.domain.response;

public record AuthenticationResponse(
        String redirect,
        String accessToken,
        String refreshToken
) {
    public static AuthenticationResponse of(String redirect, String accessToken, String refreshToken){
        return new AuthenticationResponse(
                redirect,
                accessToken,
                refreshToken
        );
    }
}
