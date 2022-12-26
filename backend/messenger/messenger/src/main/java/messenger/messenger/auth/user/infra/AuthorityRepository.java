package messenger.messenger.auth.user.infra;

import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @EntityGraph(attributePaths = "user")
    List<Authority> findAuthorityByUser(Users user);
}
