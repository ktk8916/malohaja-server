package malohaja.speak.member.domain.response;

public record NicknameCheckResponse(
        boolean isDuplicated
) {
    public static NicknameCheckResponse of(boolean isDuplicated){
        return new NicknameCheckResponse(isDuplicated);
    }
}
