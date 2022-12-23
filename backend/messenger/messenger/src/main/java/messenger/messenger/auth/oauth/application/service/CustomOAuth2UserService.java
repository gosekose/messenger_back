package messenger.messenger.auth.oauth.application.service;

import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.oauth.application.converter.ProviderUserConverter;
import messenger.messenger.auth.oauth.application.converter.ProviderUserRequest;
import messenger.messenger.auth.oauth.domain.PrincipalUser;
import messenger.messenger.auth.oauth.domain.social.ProviderUser;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.infra.UserRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements
        OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public CustomOAuth2UserService(UserRepository userRepository, UserService userService,
                                   ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
        super(userRepository, userService, providerUserConverter);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);

        ProviderUser providerUser = super.providerUser(providerUserRequest);

        super.register(providerUser, userRequest);

        return new PrincipalUser(providerUser);

    }
}
