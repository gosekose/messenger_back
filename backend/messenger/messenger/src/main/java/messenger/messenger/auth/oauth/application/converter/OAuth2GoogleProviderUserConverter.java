package messenger.messenger.auth.oauth.application.converter;

import messenger.messenger.auth.oauth.domain.social.GoogleUser;
import messenger.messenger.auth.oauth.domain.social.ProviderUser;
import messenger.messenger.auth.oauth.infra.common.enums.SocialType;
import messenger.messenger.auth.oauth.infra.common.util.OAuth2Utils;

public final class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.getClientRegistration().getRegistrationId().equals(
                SocialType.GOOGLE.getSocialName()
        )) return null;

        return new GoogleUser(
                OAuth2Utils.getMainAttributes(providerUserRequest.getOAuth2User()),
                providerUserRequest.getOAuth2User(),
                providerUserRequest.getClientRegistration()
        );
    }
}
