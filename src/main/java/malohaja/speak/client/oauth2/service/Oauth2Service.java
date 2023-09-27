package malohaja.speak.client.oauth2.service;

import lombok.RequiredArgsConstructor;
import malohaja.speak.client.oauth2.domain.Oauth2Provider;
import malohaja.speak.client.oauth2.domain.Oauth2TokenDto;
import malohaja.speak.client.oauth2.domain.Oauth2UserInfoDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Oauth2Service {

    private final Oauth2ProviderFactory factory;

    public Oauth2UserInfoDto getUserResource(String providerName, String code) {
        Oauth2Provider provider = factory.getByProviderName(providerName);

        Oauth2TokenDto tokenDto = getToken(provider, code);

        return getUserProfile(providerName, provider, tokenDto);
    }

    /**
     * oauth2 서버에서 가져온 유저 정보를 가공하는 메서드
     * 지금은 oauth2에서 제공해주는 id만 사용하고 있는 중
     * 후에 정보가 더 필요하다면 여기서 추가하면 됨
     */
    private Oauth2UserInfoDto getUserProfile(String providerName, Oauth2Provider provider, Oauth2TokenDto tokenDto) {
        Map<String, Object> userAttributes = getUserAttributes(provider, tokenDto);
        return new Oauth2UserInfoDto(providerName, String.valueOf(userAttributes.get("id")));
    }

    private Map<String, Object> getUserAttributes(Oauth2Provider provider, Oauth2TokenDto tokenDto) {
        return WebClient.create()
                .get()
                .uri(provider.getUserInfoUri())
                .headers(header -> header.setBearerAuth(tokenDto.accessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();
    }

    private Oauth2TokenDto getToken(Oauth2Provider provider, String code) {
        return WebClient.create()
                .post()
                .uri(provider.getTokenUri())
                .headers(header -> {
                    header.setBasicAuth(provider.getClientId(), provider.getClientSecret());
                    header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                    header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .bodyValue(createTokenRequest(code, provider))
                .retrieve()
                .bodyToMono(Oauth2TokenDto.class)
                .block();
    }

    private MultiValueMap<String, String> createTokenRequest(String code, Oauth2Provider provider) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("client_id", provider.getClientId());
        formData.add("client_secret", provider.getClientSecret());
        formData.add("grant_type", "authorization_code");
        formData.add("redirect_uri", provider.getRedirectUri());
        return formData;
    }
}
