package malohaja.speak.domain.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SkillType {

    JAVA(BelongType.LANGUAGE),
    SPRING(BelongType.FRAMEWORK),
    MYSQL(BelongType.DATABASE),
    GIT(BelongType.ETC);

    private final BelongType belongType;
}
