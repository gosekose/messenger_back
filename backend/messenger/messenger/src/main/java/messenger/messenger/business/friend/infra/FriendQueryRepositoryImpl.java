package messenger.messenger.business.friend.infra;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import messenger.messenger.auth.user.domain.QUsers;
import messenger.messenger.business.friend.domain.QFriend;
import messenger.messenger.business.friend.infra.query.condition.MyFriendsCondition;
import messenger.messenger.business.friend.infra.query.condition.MyFriendsSearchCondition;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import messenger.messenger.business.friend.infra.query.dto.QMyFriendsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static messenger.messenger.auth.user.domain.QUsers.*;
import static messenger.messenger.business.friend.domain.QFriend.*;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<MyFriendsDto> getMyFriendsDtoPageComplex(MyFriendsCondition condition, Pageable pageable) {

        List<MyFriendsDto> content = query
                .select(
                        new QMyFriendsDto(
                                friend.resUser.id,
                                friend.resUser.username))
                .from(friend)
                .join(friend.resUser, users)
                .where(
                        friend.reqUser.id.eq(condition.getId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(friend.count())
                .from(friend)
                .join(friend.resUser, users)
                .where(
                        friend.reqUser.id.eq(condition.getId())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<MyFriendsDto> getMyFriendsSearchDtoPageComplex(MyFriendsSearchCondition condition, Pageable pageable) {

        List<MyFriendsDto> content = query
                .select(
                        new QMyFriendsDto(
                                friend.id,
                                friend.resUser.username
                        ))
                .from(friend)
                .join(friend.resUser, users)
                .where(
                        friend.reqUser.id.eq(condition.getId()),
                        friend.reqUser.username.contains(condition.getName())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(friend.count())
                .from(friend)
                .join(friend.resUser, users)
                .where(
                        friend.reqUser.id.eq(condition.getId()),
                        friend.reqUser.username.contains(condition.getName())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }
}
