package messenger.messenger.business.friend.infra;

import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.friend.domain.Friend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Friend findByReqUserAndResUser(Users reqUser, Users resUser);


}
