package malohaja.speak.client.oauth2;

import java.util.HashMap;
import java.util.Map;

public class Oauth2ProviderFactory {

    private final Map<String, Oauth2Provider> providers;

    public Oauth2ProviderFactory(Map<String, Oauth2Provider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public Oauth2Provider getByProviderName(String name) {
        return providers.get(name);
    }
}
