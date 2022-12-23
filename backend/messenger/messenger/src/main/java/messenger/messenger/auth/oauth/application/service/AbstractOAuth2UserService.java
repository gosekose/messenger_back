package messenger.messenger.auth.oauth.application.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.oauth.application.converter.ProviderUserConverter;
import messenger.messenger.auth.oauth.application.converter.ProviderUserRequest;
import messenger.messenger.auth.oauth.domain.social.*;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.user.infra.UserRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
public abstract class AbstractOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    public AbstractOAuth2UserService(UserRepository userRepository,
                                     UserService userService,
                                     ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.providerUserConverter = providerUserConverter;
    }

    public void register(ProviderUser providerUser, OAuth2UserRequest oAuth2UserRequest) {
        Users user = userRepository.findByEmail(providerUser.getEmail());

        if (user == null) {
            ClientRegistration clientRegistration = oAuth2UserRequest.getClientRegistration();
            userService.register(clientRegistration.getRegistrationId(), providerUser);
        }

    }


    public ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
    }

}
