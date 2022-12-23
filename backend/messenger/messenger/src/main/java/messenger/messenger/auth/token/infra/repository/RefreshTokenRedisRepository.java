package messenger.messenger.auth.token.infra.repository;

import messenger.messenger.auth.token.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
