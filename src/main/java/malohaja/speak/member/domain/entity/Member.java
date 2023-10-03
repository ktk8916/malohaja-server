package malohaja.speak.member.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import malohaja.speak.global.domain.BaseEntity;
import malohaja.speak.global.domain.skill.SkillType;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // oauth2
    private String providerId;
    private String providerName;
    
    //회원정보
    private String nickname;
    private String introduction;
    private String profileImageUri;
    private Set<SkillType> skills = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private CareerType careerType;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private JobType jobType;

    public void signup(String nickname, String introduction, Set<SkillType> skills, CareerType careerType, JobType jobType){
        this.role = Role.MEMBER;
        this.nickname = nickname;
        this.introduction = introduction;
        this.skills = skills;
        this.careerType = careerType;
        this.jobType = jobType;
    }

    public static Member createMemberWithOauth2(String providerName, String providerId){
        return Member.builder()
                .providerName(providerName)
                .providerId(providerId)
                .role(Role.UNAFFILIATED)
                .build();
    }

    public static Member fromId(Long id){
        Member member = new Member();
        member.id = id;
        return member;
    }

    @Builder
    public Member(String providerId, String providerName, String nickname, String profileImageUri, Role role) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.nickname = nickname;
        this.profileImageUri = profileImageUri;
        this.role = role;
    }
}
