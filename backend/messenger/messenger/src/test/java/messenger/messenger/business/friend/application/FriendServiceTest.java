package messenger.messenger.business.friend.application;

import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.user.infra.UserRepository;
import messenger.messenger.business.friend.infra.FriendRepository;
import messenger.messenger.business.friend.presentation.dto.MyFriendSearchReqDto;
import messenger.messenger.business.friend.presentation.dto.MyFriendsReqDto;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired FriendService friendService;

    static Users reqUser;
    static Users resUser;
    static Users resUser2;
    @Autowired
    private FriendRepository friendRepository;

    @Test
    public void 친구_저장_찾기() throws Exception {
        //given
        Long saveId = friendService.save(reqUser, resUser);

        //when
        Users findId = friendService.findResUserFromFriendId(saveId);

        //then
        assertThat(resUser).isEqualTo(findId);
    }

    @Test
    public void 친구_삭제() throws Exception {
        //given
        Long saveId = friendService.save(reqUser, resUser);

        //when
        friendService.deleteFriend(friendService.findOne(saveId));

        //then
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> friendService.findOne(saveId)
        );

    }

    @Test
    public void 친구_목록_조회() throws Exception {
        //given
        friendService.save(reqUser, resUser);
        friendService.save(reqUser, resUser2);

        //when
        MyFriendsReqDto reqDto = MyFriendsReqDto.builder().build();
        Page<MyFriendsDto> myFriendsDtoPageComplex = friendService.getMyFriendsDtoPageComplex(reqDto, reqUser.getId());

        //then
        assertThat(myFriendsDtoPageComplex.getTotalElements()).isEqualTo(2);
        assertThat(myFriendsDtoPageComplex.getSize()).isEqualTo(10);
        assertThat(myFriendsDtoPageComplex.getTotalPages()).isEqualTo(1);
    }

    @Test
    public void 친구_목록_조회_페이징() throws Exception {
        //given
        friendService.save(reqUser, resUser);
        friendService.save(reqUser, resUser2);

        //when
        MyFriendsReqDto reqDto = MyFriendsReqDto.builder().page(2).build();
        Page<MyFriendsDto> myFriendsDtoPageComplex = friendService.getMyFriendsDtoPageComplex(reqDto, reqUser.getId());

        //then
        assertThat(myFriendsDtoPageComplex.getTotalElements()).isEqualTo(2);
        assertThat(myFriendsDtoPageComplex.getSize()).isEqualTo(10);
        assertThat(myFriendsDtoPageComplex.getTotalPages()).isEqualTo(1);
        assertThat(myFriendsDtoPageComplex.getContent().size()).isEqualTo(0);
    }

    @Test
    public void 친구_목록_이름_조회() throws Exception {
        //given
        friendService.save(reqUser, resUser);
        friendService.save(reqUser, resUser2);

        //when
        MyFriendSearchReqDto reqDto = MyFriendSearchReqDto.builder().name("k").build();
        Page<MyFriendsDto> resDto = friendService.getMyFriendsSearchDtoPageComplex(reqDto, reqUser.getId());

        //then
        assertThat(resDto.getTotalElements()).isEqualTo(2);
        assertThat(resDto.getSize()).isEqualTo(10);

    }



    @BeforeEach
    public void dataInit() {
        reqUser = Users.builder().username("k1").email("k1").build();
        resUser = Users.builder().username("k2").email("k2").build();
        resUser2 = Users.builder().username("k3").email("k3").build();

        userRepository.save(reqUser);
        userRepository.save(resUser);
        userRepository.save(resUser2);

        reqUser = userRepository.findByEmail("k1");
        resUser = userRepository.findByEmail("k2");
        resUser2 = userRepository.findByEmail("k3");
    }

}