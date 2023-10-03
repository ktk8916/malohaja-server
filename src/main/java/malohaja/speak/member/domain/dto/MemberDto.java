package malohaja.speak.member.domain.dto;

import malohaja.speak.member.domain.entity.Member;

public record MemberDto(
        String nickname,
        String profileImageUri
) {
    public static MemberDto fromEntity(Member member){
        return new MemberDto(member.getNickname(), member.getProfileImageUri());
    }
}
