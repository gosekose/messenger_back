package messenger.messenger.business.chatroom.infra;

import messenger.messenger.business.chatroom.domain.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {

}
