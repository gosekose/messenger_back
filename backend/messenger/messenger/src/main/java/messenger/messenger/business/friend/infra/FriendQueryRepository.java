package messenger.messenger.business.friend.infra;

import messenger.messenger.business.friend.infra.query.condition.MyFriendsCondition;
import messenger.messenger.business.friend.infra.query.condition.MyFriendsSearchCondition;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendQueryRepository {

    Page<MyFriendsDto> getMyFriendsDtoPageComplex(MyFriendsCondition condition, Pageable pageable);
    Page<MyFriendsDto> getMyFriendsSearchDtoPageComplex(MyFriendsSearchCondition condition, Pageable pageable);


}
