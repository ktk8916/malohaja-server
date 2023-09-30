package malohaja.speak.client.oauth2;

import malohaja.speak.client.oauth2.domain.Oauth2Provider;
import malohaja.speak.client.oauth2.exception.NoSupportedOauth2Exception;
import malohaja.speak.client.oauth2.service.Oauth2ProviderFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class Oauth2ProviderFactoryTest {

    @Autowired
    private Oauth2ProviderFactory oauth2ProviderFactory;

    @DisplayName("유효하지 않은 providerName인 경우, 예외를 발생시킨다.")
    @Test
    void inValidOauth2ProviderName(){
        // given
        String inValidOauth2ProviderName = "그냥누가봐도틀린이름임";

        // when, then
        assertThatThrownBy(()->oauth2ProviderFactory.getByProviderName(inValidOauth2ProviderName))
                .isInstanceOf(NoSupportedOauth2Exception.class);
    }

    private static Stream<Arguments> oauth2ProviderTokenUri(){
        return Stream.of(
                Arguments.of("github", "https://github.com/login/oauth/access_token", "https://api.github.com/user"),
                Arguments.of("kakao", "https://kauth.kakao.com/oauth/token", "https://kapi.kakao.com/v2/user/me")
        );
    }

    @DisplayName("providerName으로 생성된 Oauth2Provider를 가져온다.")
    @ParameterizedTest
    @MethodSource("oauth2ProviderTokenUri")
    void getByProviderName(String providerName, String tokenUri, String userInfoUri){
        // given
        // oauth2ProviderTokenUri()

        // when
        Oauth2Provider provider = oauth2ProviderFactory.getByProviderName(providerName);

        // then
        assertThat(provider.getTokenUri()).isEqualTo(tokenUri);
        assertThat(provider.getUserInfoUri()).isEqualTo(userInfoUri);
    }
}