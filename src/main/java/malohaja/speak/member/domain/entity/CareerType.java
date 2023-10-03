package malohaja.speak.member.domain.entity;

import lombok.Getter;
import malohaja.speak.member.exception.NoMatchingCareerException;

import java.util.Arrays;

@Getter
public enum CareerType {
    BEGINNER,
    JUNIOR,
    MIDDLE,
    SENIOR,
    LEAD,
    ;

    public static CareerType fromString(String career){
        return Arrays.stream(CareerType.values())
                .filter(careerType -> careerType.name().equalsIgnoreCase(career))
                .findFirst()
                .orElseThrow(NoMatchingCareerException::new);
    }
}
