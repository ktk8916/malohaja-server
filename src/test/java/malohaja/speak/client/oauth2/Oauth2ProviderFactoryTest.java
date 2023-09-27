package malohaja.speak.client.oauth2;

import malohaja.speak.client.oauth2.domain.Oauth2Provider;
import malohaja.speak.client.oauth2.service.Oauth2ProviderFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

@SpringBootTest
@ActiveProfiles("dev")
class Oauth2ProviderFactoryTest {

    @Autowired
    private Oauth2ProviderFactory oauth2ProviderFactory;

    private static Stream<Arguments> oauth2ProviderTokenUri(){
        return Stream.of(
                Arguments.of("github", "https://github.com/login/oauth/access_token"),
                Arguments.of("google", "https://www.googleapis.com/oauth2/v4/token")
        );
    }

    @DisplayName("providerName으로 생성된 Oauth2Provider를 가져온다.")
    @ParameterizedTest
    @MethodSource("oauth2ProviderTokenUri")
    void getByProviderName(String providerName, String tokenUri){
        //given
        Oauth2Provider provider = oauth2ProviderFactory.getByProviderName(providerName);

        //when, then
        Assertions.assertThat(provider.getTokenUrl()).isEqualTo(tokenUri);
    }
}