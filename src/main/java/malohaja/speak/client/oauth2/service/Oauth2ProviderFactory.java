package malohaja.speak.client.oauth2.service;

import malohaja.speak.client.oauth2.domain.Oauth2Provider;

import java.util.HashMap;
import java.util.Map;

public class Oauth2ProviderFactory {

    private final Map<String, Oauth2Provider> providers;

    public Oauth2ProviderFactory(Map<String, Oauth2Provider> providers) {
        this.providers = new HashMap<>(providers);
    }

    public Oauth2Provider getByProviderName(String name) {
        if(!isValidOauth2Provider(name)){
            throw new IllegalArgumentException("not supported oauth2 provider");
        }

        return providers.get(name);
    }

    private boolean isValidOauth2Provider(String name){
        return providers.containsKey(name);
    }
}
