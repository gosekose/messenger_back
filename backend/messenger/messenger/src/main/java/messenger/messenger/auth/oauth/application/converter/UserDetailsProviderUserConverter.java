package messenger.messenger.auth.oauth.application.converter;

import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.oauth.domain.form.FormUser;
import messenger.messenger.auth.oauth.domain.social.ProviderUser;
import messenger.messenger.auth.user.domain.Users;


@Slf4j
public final class UserDetailsProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        Users user = providerUserRequest.getUser();
        if (user == null) {
            return null;
        }

        return FormUser.builder()
                .id(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .provider("none")
                .build();
    }
}
