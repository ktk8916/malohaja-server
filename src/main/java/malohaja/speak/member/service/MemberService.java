package malohaja.speak.member.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.global.domain.skill.SkillType;
import malohaja.speak.global.jwt.TokenInfo;
import malohaja.speak.member.domain.entity.CareerType;
import malohaja.speak.member.domain.entity.JobType;
import malohaja.speak.member.domain.entity.Member;
import malohaja.speak.member.domain.request.SignupRequest;
import malohaja.speak.member.exception.MemberNotFoundException;
import malohaja.speak.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void signup(TokenInfo tokenInfo, SignupRequest request){
        Member member = getMemberByOauth2(tokenInfo.getProviderName(), tokenInfo.getProviderId());

        member.signup(
                request.nickName(),
                request.introduction(),
                SkillType.convertList(request.skills()),
                CareerType.fromString(request.careerType()),
                JobType.fromString(request.jobType())
        );
    }

    private Member getMemberByOauth2(String providerName, String providerId) {
        return memberRepository.findByProviderNameAndProviderId(providerName, providerId)
                .orElseThrow(MemberNotFoundException::new);
    }
}
