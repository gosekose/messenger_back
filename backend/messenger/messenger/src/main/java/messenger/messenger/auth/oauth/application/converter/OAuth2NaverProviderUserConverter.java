package messenger.messenger.auth.oauth.application.converter;

import messenger.messenger.auth.oauth.domain.social.NaverUser;
import messenger.messenger.auth.oauth.domain.social.ProviderUser;
import messenger.messenger.auth.oauth.infra.common.enums.SocialType;
import messenger.messenger.auth.oauth.infra.common.util.OAuth2Utils;

public final class OAuth2NaverProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.getClientRegistration().getRegistrationId().equals(
                SocialType.NAVER.getSocialName()
        )) return null;

        return new NaverUser(
                OAuth2Utils.getSubAttributes(providerUserRequest.getOAuth2User(), "response"),
                providerUserRequest.getOAuth2User(),
                providerUserRequest.getClientRegistration()
        );

    }
}
