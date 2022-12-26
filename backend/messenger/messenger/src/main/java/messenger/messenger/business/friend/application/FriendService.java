package messenger.messenger.business.friend.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.friend.domain.JoinStatus;
import messenger.messenger.business.friend.presentation.dto.MyFriendSearchReqDto;
import messenger.messenger.business.friend.presentation.dto.MyFriendsReqDto;
import messenger.messenger.business.friend.domain.Friend;
import messenger.messenger.business.friend.infra.FriendQueryRepositoryImpl;
import messenger.messenger.business.friend.infra.FriendRepository;
import messenger.messenger.business.friend.infra.query.condition.MyFriendsCondition;
import messenger.messenger.business.friend.infra.query.condition.MyFriendsSearchCondition;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import messenger.messenger.business.friend.presentation.dto.MyResUserDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FriendService {

    private final FriendQueryRepositoryImpl friendQueryRepository;
    private final FriendRepository friendRepository;
    private final UserService userService;

    /**
     *
     * 사용자의 친구 목록의 요청을 받아, 친구 리스트를 페이징화하여 제공
     *
     * @param reqDto
     * @param userId
     * @return
     */
    public Page<MyFriendsDto> getMyFriendsDtoPageComplex(MyFriendsReqDto reqDto, Long userId) {

        reqDto.updatePageable();
        log.info("userId = {}", userId);

        Pageable pageable = PageRequest.of(reqDto.getPage(), reqDto.getSize());
        MyFriendsCondition condition = new MyFriendsCondition(userId);

        return friendQueryRepository.getMyFriendsDtoPageComplex(condition, pageable);
    }

    /**
     * 친구 저장
     */
    @Transactional
    public Long save(Users reqUser, Users resUser) {

        if (reqUser.getId() != resUser.getId()) {

            Long friendId = friendRepository.save(new Friend(reqUser, resUser, JoinStatus.JOIN_STATUS)).getId();
            log.info("friendId = {}", friendId);

            return friendId;
        } else {
            throw new IllegalArgumentException("본인을 친구추가 할 수 없습니다.");
        }

    }

    /**
     * 친구 Id로 친구 객체 찾기
     *
     */
    public Friend findOne(Long id) {
        Optional<Friend> findFriend = friendRepository.findById(id);
        return findFriend.orElseThrow(() -> {throw new IllegalArgumentException("존재하지 않는 회원입니다.");});
    }


    /**
     * 등록한 친구 목록의 유저 찾기
     */
    public Users findResUserFromFriendId(Long friendId) {

        Optional<Friend> findFriend = friendRepository.findById(friendId);
        return findFriend.orElseThrow(() -> {throw new IllegalArgumentException("존재하지 않는 회원입니다.");}).getResUser();
    }

    /**
     * 친구 삭제
     */
    @Transactional
    public void deleteFriend(Friend friend) {
        friendRepository.delete(friend);
    }

    /**
     *
     * 유저 아이디와 검색한 유저의 이름으로 부터 친구 목록 찾기
     *
     * @param reqDto (찾으려는 친구 유저 이름, 페이지, 사이즈)
     * @param userId (유저 Id)
     * @return
     */
    public Page<MyFriendsDto> getMyFriendsSearchDtoPageComplex(MyFriendSearchReqDto reqDto, Long userId) {

        reqDto.updatePageable();
        MyFriendsSearchCondition condition = new MyFriendsSearchCondition(userId, reqDto.getName());
        PageRequest pageable = PageRequest.of(reqDto.getPage(), reqDto.getSize());

        return friendQueryRepository.getMyFriendsSearchDtoPageComplex(condition, pageable);
    }


    /**
     *
     * 특정 친구 숨김 기능
     */
    @Transactional
    public void applyHiddenFriend(Users reqUser, MyResUserDto resUserDto) {
        Users resUser = userService.findOne(resUserDto.getUserId());
        Friend friend = friendRepository.findByReqUserAndResUser(reqUser, resUser);
        friend.updateJoinStatus(JoinStatus.HIDDEN_STATUS);
    }

    /**
     * reqUser와 resUser로 친구 객체 찾기
     *
     */
    public Friend findByReqUserAndResUser(Users reqUser, Users resUser) {
        return friendRepository.findByReqUserAndResUser(reqUser, resUser);
    }
}
