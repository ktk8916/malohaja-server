package malohaja.speak.auth.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.auth.domain.response.AuthenticationResponse;
import malohaja.speak.client.oauth2.domain.Oauth2UserInfoDto;
import malohaja.speak.client.oauth2.service.Oauth2Service;
import malohaja.speak.global.jwt.JwtService;
import malohaja.speak.member.entity.Member;
import malohaja.speak.member.entity.Role;
import malohaja.speak.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final Oauth2Service oauth2Service;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    public AuthenticationResponse authenticate(String providerName, String code) {
        Oauth2UserInfoDto userInfoDto = oauth2Service.getUserResource(providerName, code);

        Member member = checkMember(userInfoDto.providerName(), userInfoDto.providerId());

        return AuthenticationResponse.of(
                getRedirect(member),
                jwtService.generateToken(member)
        );
    }

    private Member checkMember(String providerName, String providerId) {
        return memberRepository
                .findByProviderNameAndProviderId(providerName, providerId)
                .orElseGet(() -> memberRepository.save(
                        Member.createMemberWithOauth2(providerName, providerId)
                ));
    }

    private String getRedirect(Member member){
        return member.getRole()== Role.UNAFFILIATED ? "/signup" : "/";
    }
}

