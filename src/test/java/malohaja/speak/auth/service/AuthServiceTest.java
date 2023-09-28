package malohaja.speak.auth.service;

import malohaja.speak.auth.domain.response.AuthenticationResponse;
import malohaja.speak.client.oauth2.domain.Oauth2UserInfoDto;
import malohaja.speak.client.oauth2.service.Oauth2Service;
import malohaja.speak.member.entity.Member;
import malohaja.speak.member.entity.Role;
import malohaja.speak.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("dev")
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private Oauth2Service oauth2Service;

    /*
    TODO
     1. 가입완료한 회원
     2. 추가정보 미기입 회원
     3. 신규 회원
     */
    @DisplayName("가입 완료한 회원인 경우 token과 함께 메인 페이지로 안내한다")
    @Test
    void existingMember(){
        // given
        String providerName = "kakao";
        String providerCode = "kakaoCode";
        String providerId = "kakaoId";

        Member member = Member.builder()
                .providerName(providerName)
                .providerId(providerId)
                .role(Role.MEMBER)
                .build();

        memberRepository.save(member);

        Oauth2UserInfoDto userInfoDto = new Oauth2UserInfoDto(providerName, providerId);

        Mockito.when(oauth2Service.getUserResource(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(userInfoDto);

        // when
        AuthenticationResponse response = authService.authenticate(providerName, providerCode);

        // then
        assertThat(response.redirect()).isEqualTo("/");
        assertThat(response.accessToken()).isNotNull();
    }

    @DisplayName("추가 정보를 입력하지 않은 회원인 경우 token과 함께 회원가입 페이지로 안내한다")
    @Test
    void unaffiliatedMember(){
        // given
        String providerName = "kakao";
        String providerCode = "kakaoCode";
        String providerId = "kakaoId";

        Member member = Member.builder()
                .providerName(providerName)
                .providerId(providerId)
                .role(Role.UNAFFILIATED)
                .build();

        memberRepository.save(member);

        Oauth2UserInfoDto userInfoDto = new Oauth2UserInfoDto(providerName, providerId);

        Mockito.when(oauth2Service.getUserResource(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(userInfoDto);

        // when
        AuthenticationResponse response = authService.authenticate(providerName, providerCode);

        // then
        assertThat(response.redirect()).isEqualTo("/signup");
        assertThat(response.accessToken()).isNotNull();
    }

    @DisplayName("신규 가입인 경우")
    @Nested
    class NewMember{

        private final String providerName = "kakao";
        private final String providerCode = "kakaoCode";
        private final String providerId = "kakaoId";
        private final Oauth2UserInfoDto userInfoDto = new Oauth2UserInfoDto(providerName, providerId);
        @DisplayName("member를 저장한다.")
        @Test
        void saveMember(){
            // given
            Mockito.when(oauth2Service.getUserResource(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(userInfoDto);

            // when
            AuthenticationResponse response = authService.authenticate(providerName, providerCode);

            // then
            Optional<Member> member = memberRepository.findByProviderNameAndProviderId(providerName, providerId);
            assertThat(member).isPresent();
        }

        @DisplayName("token과 함께 회원가입 페이지로 안내한다.")
        @Test
        void redirectSignupWithAccessToken(){
            // given
            Mockito.when(oauth2Service.getUserResource(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(userInfoDto);

            // when
            AuthenticationResponse response = authService.authenticate(providerName, providerCode);

            // then
            assertThat(response.accessToken()).isNotNull();
            assertThat(response.redirect()).isEqualTo("/signup");
        }
    }

}



