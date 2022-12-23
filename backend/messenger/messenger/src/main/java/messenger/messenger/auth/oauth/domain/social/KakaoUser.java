package messenger.messenger.auth.oauth.domain.social;

import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.oauth.domain.Attributes;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Slf4j
public class KakaoUser extends OAuth2ProviderUser {

    private final Map<String, Object> subAttributes;


    public KakaoUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
        this.subAttributes = attributes.getOtherAttributes();
    }

    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    @Override
    public String getUsername() {
        return (String) subAttributes.get("nickname");
    }

    @Override
    public String getPicture() {
        return (String) subAttributes.get("profile_image_url");
    }
}
