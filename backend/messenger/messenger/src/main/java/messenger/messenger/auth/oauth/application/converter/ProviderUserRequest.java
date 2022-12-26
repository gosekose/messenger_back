package messenger.messenger.auth.oauth.application.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import messenger.messenger.auth.user.domain.Users;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class ProviderUserRequest {

    private final ClientRegistration clientRegistration;
    private final OAuth2User oAuth2User;
    private final Users user;

    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this.clientRegistration = clientRegistration;
        this.oAuth2User = oAuth2User;
        this.user = null;
    }

    public ProviderUserRequest(Users user) {
        this.clientRegistration = null;
        this.oAuth2User = null;
        this.user = user;
    }
}


