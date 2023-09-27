package malohaja.speak.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    UNAFFILIATED("미가입"),
    MEMBER("회원");

    private final String text;
}
