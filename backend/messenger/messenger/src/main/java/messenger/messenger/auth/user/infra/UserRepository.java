package messenger.messenger.auth.user.infra;

import messenger.messenger.auth.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

    Users findByEmail(String email);

}
