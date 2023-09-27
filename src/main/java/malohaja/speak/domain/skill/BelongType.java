package malohaja.speak.domain.skill;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BelongType {
    
    LANGUAGE("언어"),
    FRAMEWORK("프레임워크"),
    DATABASE("데이터베이스"),
    ETC("기타");

    private final String text;

}
