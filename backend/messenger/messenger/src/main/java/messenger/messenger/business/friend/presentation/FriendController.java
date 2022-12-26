package messenger.messenger.business.friend.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.friend.application.FriendService;
import messenger.messenger.business.friend.domain.Friend;
import messenger.messenger.business.friend.presentation.dto.MyFriendSearchReqDto;
import messenger.messenger.business.friend.presentation.dto.MyFriendsReqDto;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import messenger.messenger.business.friend.presentation.dto.MyResUserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/friends")
public class FriendController {

    private final FriendService friendService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity getMyFriends(@RequestBody MyFriendsReqDto reqDto) {

        Users user = userService.findUserForAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Page<MyFriendsDto> myFriends = friendService.getMyFriendsDtoPageComplex(reqDto, user.getId());

        return new ResponseEntity(myFriends, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity addFriend(@RequestBody MyResUserDto resUserDto) {

        Users user = userService.findUserForAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Users resUser = userService.findOne(resUserDto.getUserId());
        friendService.save(user, resUser);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteFriend(@RequestBody MyResUserDto resUserDto) {

        Users reqUser = userService.findUserForAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Users resUser = userService.findOne(resUserDto.getUserId());

        Friend friend = friendService.findByReqUserAndResUser(reqUser, resUser);

        if (friend != null) {
            friendService.deleteFriend(friend);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchFriend(@RequestBody MyFriendSearchReqDto reqDto) {

        Users user = userService.findUserForAuthentication(SecurityContextHolder.getContext().getAuthentication());
        Page<MyFriendsDto> resDto = friendService.getMyFriendsSearchDtoPageComplex(reqDto, user.getId());

        return new ResponseEntity(resDto, HttpStatus.OK);
    }

    @PostMapping("/hidden")
    public ResponseEntity applyHiddenFriend(@RequestBody MyResUserDto resUserDto) {

        Users reqUser = userService.findUserForAuthentication(SecurityContextHolder.getContext().getAuthentication());

        friendService.applyHiddenFriend(reqUser, resUserDto);

        return new ResponseEntity(HttpStatus.OK);

    }

}
