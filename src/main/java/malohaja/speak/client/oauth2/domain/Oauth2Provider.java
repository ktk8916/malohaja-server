package malohaja.speak.client.oauth2.domain;

import lombok.Builder;
import lombok.Getter;
import malohaja.speak.client.oauth2.config.Oauth2Properties;

@Getter
public class Oauth2Provider {

    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String tokenUrl;
    private final String userInfoUrl;

    public Oauth2Provider(Oauth2Properties.Client client) {
        this(
                client.getClientId(),
                client.getClientSecret(),
                client.getRedirectUri(),
                client.getTokenUri(),
                client.getUserInfoUri()
        );
    }

    @Builder
    public Oauth2Provider(String clientId, String clientSecret, String redirectUrl, String tokenUrl, String userInfoUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
    }
}
