package messenger.messenger.auth.token.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.token.application.AuthService;
import messenger.messenger.auth.token.infra.repository.TokenRepositoryImpl;
import messenger.messenger.auth.user.application.AuthorityService;
import messenger.messenger.auth.user.application.dto.FormRegisterUserDto;
import messenger.messenger.auth.user.application.UserService;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.token.presentation.dto.LoginDto;
import messenger.messenger.auth.token.presentation.dto.TokenAuthDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authorityService;

    private final AuthService authService;

    /**
     *
     * 회원 가입 요청
     * BAD_REQUEST: 400 (요청한 파라미터의 타입 에러 혹은 바인딩 에러)
     * CONFLICT: 409 (요청한 회원가입 이메일 이미 존재)
     * OK: 200 (가입 완료)
     *
     * @param formRegisterUserDto
     * @param bindingResult
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity formRegister(@Valid @RequestBody FormRegisterUserDto formRegisterUserDto,
                                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!userService.registerForm(formRegisterUserDto, passwordEncoder)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/logout")
    public ResponseEntity logout(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("RefreshToken") String refreshToken) {

        authService.logout(accessToken, refreshToken);

        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping("/test")
    public ResponseEntity tokenTest(@RequestHeader(value = "Authorization") String accessToken) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto,
                                BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Users user = userService.userLoginCheck(loginDto, passwordEncoder);
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<Authority> authorities = authorityService.findAuthorityByUser(user);
        if (authorities.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity(authService.createFormTokenAuth(user.getEmail(), authorities), HttpStatus.OK);
    }


    @PostMapping("/reissue")
    public ResponseEntity reissueToken(@RequestHeader(value = "RefreshToken") String refreshToken) {

        TokenAuthDto tokenAuthDto = authService.reissue(refreshToken);

        return new ResponseEntity(tokenAuthDto, HttpStatus.OK);

    }


}
