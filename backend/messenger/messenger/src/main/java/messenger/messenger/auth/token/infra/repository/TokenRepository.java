package messenger.messenger.auth.token.infra.repository;

import messenger.messenger.auth.token.domain.LogoutAccessToken;
import messenger.messenger.auth.token.domain.LogoutRefreshToken;
import messenger.messenger.auth.token.domain.RefreshToken;

public interface TokenRepository {

    void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken);

    void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken);

    void saveRefreshToken(RefreshToken refreshToken);

    boolean existsLogoutAccessTokenById(String token);

    boolean existLogoutRefreshTokenById(String token);

    boolean existRefreshTokenById(String token);

    void deleteRefreshTokenById(String token);

}
