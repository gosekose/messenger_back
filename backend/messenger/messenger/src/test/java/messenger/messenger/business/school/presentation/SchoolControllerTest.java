package messenger.messenger.business.school.presentation;

import com.google.gson.Gson;
import messenger.messenger.auth.token.application.AuthService;
import messenger.messenger.auth.token.presentation.dto.TokenAuthDto;
import messenger.messenger.auth.user.application.AuthorityService;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.application.dto.FormRegisterUserDto;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.school.application.SchoolService;
import messenger.messenger.business.school.application.dto.SchoolSaveDto;
import messenger.messenger.business.school.application.dto.SchoolSearchReqDto;
import messenger.messenger.business.school.infra.repository.query.dto.SchoolSearchDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {

    @Autowired UserService userService;
    @Autowired AuthService authService;
    @Autowired AuthorityService authorityService;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired SchoolService schoolService;
    @Mock SchoolController schoolController;

    static Users user;
    static List<Authority> authorities;
    static TokenAuthDto tokenAuthDto;
    static String accessToken;
    private MockMvc mockMvc;

    @Autowired TestRestTemplate restTemplate;

    @BeforeEach
    public void dbInit() {
        userService.registerForm(FormRegisterUserDto.builder().email("k@naver.com").username("kose").password("1234").build(), passwordEncoder);
        user = userService.findByEmail("k@naver.com");
        authorities = authorityService.findAuthorityByUser(user);

        tokenAuthDto = authService.createFormTokenAuth("k@naver.com", authorities);
        accessToken = "Bearer " + tokenAuthDto.getAccessToken();

        mockMvc = MockMvcBuilders
                .standaloneSetup(schoolController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        saveSchool();

    }

    @Test
    public void 학교_검색() throws Exception {

        //given
        SchoolSearchReqDto reqDto = requestDto();
        Page<SchoolSearchDto> resDto = schoolService.getSchoolSearchDto(reqDto);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/schools/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(reqDto))
                        .header("Authorization", accessToken)
        );

        //then
        resultActions.andExpect(status().isOk());

    }


    @Test
    public void 학교_저장() throws Exception {

        //given
        SchoolSaveDto saveDto = new SchoolSaveDto("서울c 학교", "<a ref='www.naver.com'>", "1233");

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/schools/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(saveDto))
                        .characterEncoding("utf-8")
                        .header("Authorization", accessToken)
        );

        //then
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].schoolAddress").value("<a ref='www.naver.com'>"));

//        Assertions.assertThat(schoolService.findOne());

    }

    @Test
    public void 학교_저장_rest() throws Exception {

        //given
        SchoolSaveDto saveDto = new SchoolSaveDto("서울c 학교", "<a ref='www.naver.com'>", "1233");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/schools/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(saveDto))
                        .characterEncoding("utf-8")
                        .header("Authorization", accessToken)
        );

        //then
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].schoolAddress").value("<a ref='www.naver.com'>"));

//        Assertions.assertThat(schoolService.findOne());

    }


    @Test
    public void 학교_하나_검색() throws Exception {

        //given
        Long savedId = schoolService.save(new SchoolSaveDto("서울 D학교", "서울 D동", "12345"));

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/schools/"+savedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .header("Authorization", accessToken)
        );

        //then
        resultActions.andDo(print());
        resultActions.andExpect(status().is2xxSuccessful());

    }



    private SchoolSearchReqDto requestDto() {
        return SchoolSearchReqDto.builder().page(0).size(10).schoolName("서울").build();
    }

    private void saveSchool() {

        String[] school = {"서울A대학교", "서울B대학교", "인천A대학교"};
        String[] address = {"서울시 A구", "서울시 B구", "인천시"};
        String[] regisNum = {"123", "124", "125"};

        for (int i=0; i< school.length; i++) {
            schoolService.save(new SchoolSaveDto(school[i], address[i], regisNum[i]));
        }
    }

}