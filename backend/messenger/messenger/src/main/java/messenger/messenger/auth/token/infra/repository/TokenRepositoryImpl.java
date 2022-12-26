package messenger.messenger.auth.token.infra.repository;

import lombok.RequiredArgsConstructor;
import messenger.messenger.auth.token.domain.LogoutAccessToken;
import messenger.messenger.auth.token.domain.LogoutRefreshToken;
import messenger.messenger.auth.token.domain.RefreshToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository {

    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private final LogoutRefreshTokenRedisRepository logoutRefreshTokenRedisRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Override
    public void saveLogoutAccessToken(LogoutAccessToken logoutAccessToken) {
        logoutAccessTokenRedisRepository.save(logoutAccessToken);
    }

    @Override
    public void saveLogoutRefreshToken(LogoutRefreshToken logoutRefreshToken) {
        logoutRefreshTokenRedisRepository.save(logoutRefreshToken);
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRedisRepository.save(refreshToken);
    }

    @Override
    public boolean existsLogoutAccessTokenById(String token) {
        return logoutAccessTokenRedisRepository.existsById(token);
    }

    @Override
    public boolean existLogoutRefreshTokenById(String token) {
        return logoutRefreshTokenRedisRepository.existsById(token);
    }

    @Override
    public boolean existRefreshTokenById(String token) {
        return refreshTokenRedisRepository.existsById(token);
    }

    @Override
    public void deleteRefreshTokenById(String token) {
        refreshTokenRedisRepository.deleteById(token);
    }
}
