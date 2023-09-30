package malohaja.speak.global.jwt;

import malohaja.speak.member.entity.Member;
import malohaja.speak.member.entity.Role;
import malohaja.speak.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("dev")
@Transactional
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("member로 jwt 토큰을 생성하고, 토큰에서 member를 추출한다.")
    @Test
    void generateToken(){
        // given
        Member member = Member.builder()
                .providerName("kakao")
                .providerId("kakaoId")
                .role(Role.MEMBER)
                .build();

        Member savedMember = memberRepository.save(member);
        String token = jwtService.generateToken(member);

        // when
        TokenInfo tokenInfo = jwtService.extractUser(token);

        // then
        Assertions.assertThat(tokenInfo.getId()).isEqualTo(1L);
        Assertions.assertThat(tokenInfo.getProviderName()).isEqualTo("kakao");
        Assertions.assertThat(tokenInfo.getProviderId()).isEqualTo("kakaoId");
        Assertions.assertThat(tokenInfo.getRole()).isEqualTo(Role.MEMBER);
    }
}