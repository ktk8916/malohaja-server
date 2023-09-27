package malohaja.speak.client.oauth2;

import java.util.HashMap;
import java.util.Map;

public class Oauth2Adapter {

    private Oauth2Adapter() {}

    // OauthProperties를 OauthProvider로 변환해준다.
    public static Map<String, Oauth2Provider> getOauthProviders(Oauth2Properties properties) {
        Map<String, Oauth2Provider> oauth2Provider = new HashMap<>();

        properties.getClient()
                .forEach((key, value) -> oauth2Provider.put(key, new Oauth2Provider(value)));
        return oauth2Provider;
    }
}
