package malohaja.speak.member.entity;

import jakarta.persistence.*;
import lombok.*;
import malohaja.speak.global.domain.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String providerId;
    private String providerName;
    private String nickName;
    private String profileImageUri;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMemberWithOauth2(String providerName, String providerId){
        return Member.builder()
                .providerName(providerName)
                .providerId(providerId)
                .role(Role.UNAFFILIATED)
                .build();
    }

    @Builder
    public Member(String providerId, String providerName, String nickName, String profileImageUri, Role role) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.nickName = nickName;
        this.profileImageUri = profileImageUri;
        this.role = role;
    }
}
