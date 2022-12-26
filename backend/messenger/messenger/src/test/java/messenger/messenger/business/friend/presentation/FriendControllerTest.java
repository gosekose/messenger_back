package messenger.messenger.business.friend.presentation;

import com.google.gson.Gson;
import messenger.messenger.auth.token.application.AuthService;
import messenger.messenger.auth.token.presentation.dto.TokenAuthDto;
import messenger.messenger.auth.user.application.AuthorityService;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.application.dto.FormRegisterUserDto;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.friend.application.FriendService;
import messenger.messenger.business.friend.presentation.dto.MyFriendSearchReqDto;
import messenger.messenger.business.friend.presentation.dto.MyFriendsReqDto;
import messenger.messenger.business.friend.presentation.dto.MyCrudFriendDto;
import messenger.messenger.business.friend.infra.query.dto.MyFriendsDto;
import messenger.messenger.business.friend.presentation.dto.MyResUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class FriendControllerTest {

    @Autowired UserService userService;
    @Autowired
    AuthService authService;
    @Autowired
    AuthorityService authorityService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FriendService friendService;
    @Mock
    FriendController friendController;

    static Users user;
    static List<Authority> authorities;
    static TokenAuthDto tokenAuthDto;
    static String accessToken;

    private MockMvc mockMvc;


    @BeforeEach
    public void dbInit() {
        userService.registerForm(FormRegisterUserDto.builder().email("k@naver.com").username("kose").password("1234").build(), passwordEncoder);
        user = userService.findByEmail("k@naver.com");
        authorities = authorityService.findAuthorityByUser(user);

        tokenAuthDto = authService.createFormTokenAuth("k@naver.com", authorities);
        accessToken = "Bearer " + tokenAuthDto.getAccessToken();

        mockMvc = MockMvcBuilders.standaloneSetup(friendController).build();
        saveFriends();

    }

    @Test
    public void 친구_목록_조회() throws Exception {
        //given
        MyFriendsReqDto reqDto = new MyFriendsReqDto(null, 0, 10);
        Page<MyFriendsDto> resDto = friendService.getMyFriendsDtoPageComplex(reqDto, user.getId());

        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/friends")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(reqDto))
                        .accept(APPLICATION_JSON));

        //then
        result
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

    @Test
    public void 친구_저장() throws Exception {
        //given
        userService.registerForm(FormRegisterUserDto.builder().email("k4@naver.com").password("1234").username("k").build(), passwordEncoder);
        MyResUserDto reqDto = new MyResUserDto(userService.findByEmail("k4@naver.com").getId());

        //when
        ResultActions result = mockMvc.perform(
                post("/api/v1/friends/new")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(reqDto)));

        //then
        result
                .andExpect(status().isOk());

    }

    @Test
    public void 친구_삭제() throws Exception {
        //given
        MyResUserDto reqDto = new MyResUserDto(userService.findByEmail("k1@naver.com").getId());

        //when
        ResultActions result = mockMvc.perform(
                post("/api/v1/friends/delete")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(reqDto)));

        //then
        result.andExpect(status().isOk());

    }

    @Test
    public void 친구_목록_이름_조회() throws Exception {
        //given
        MyFriendSearchReqDto reqDto = MyFriendSearchReqDto.builder().name("se").build();

        //when
        ResultActions result = mockMvc.perform(
                get("/api/v1/friends/search")
                        .header("Authorization", accessToken)
                        .contentType(APPLICATION_JSON)
                        .content(new Gson().toJson(reqDto)));

        //then
        result.andExpect(status().isOk());

    }



    private void saveFriends() {

        String[] email = {"k1@naver.com", "k2@naver.com", "k3@naver.com"};
        String[] username = {"kose1", "kose2", "kose3"};
        String[] password = {"1234", "1234", "1234"};

        for (int i=0; i< email.length; i++) {
            userService.registerForm(FormRegisterUserDto.builder().email(email[i]).username(username[i]).password(password[i]).build(), passwordEncoder);
            friendService.save(user, userService.findByEmail(email[i]));
        }
    }

}