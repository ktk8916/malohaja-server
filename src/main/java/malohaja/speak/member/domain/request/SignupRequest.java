package malohaja.speak.member.domain.request;

import java.util.List;

public record SignupRequest(
        String nickname,
        String introduction,
        List<String> skills,
        String careerType,
        String jobType
) {
}
