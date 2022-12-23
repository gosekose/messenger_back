package messenger.messenger.auth.token.infra.repository;

import messenger.messenger.auth.token.domain.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
