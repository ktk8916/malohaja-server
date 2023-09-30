package malohaja.speak.global.domain.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import malohaja.speak.global.exception.NoMatchingSkillException;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum SkillType {

    JAVA(BelongType.LANGUAGE),
    SPRING(BelongType.FRAMEWORK),
    MYSQL(BelongType.DATABASE),
    GIT(BelongType.ETC);

    private final BelongType belongType;

    public static SkillType fromString(String skill){
        return Arrays.stream(SkillType.values())
                .filter(skillType -> skillType.name().equalsIgnoreCase(skill))
                .findFirst()
                .orElseThrow(NoMatchingSkillException::new);
    }

    public static Set<SkillType> convertList(List<String> skills){
        return skills.stream()
                .map(SkillType::fromString)
                .collect(Collectors.toSet());
    }
}
