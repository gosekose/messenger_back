package messenger.messenger.auth.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.token.presentation.exception.NotFoundUserException;
import messenger.messenger.auth.user.application.dto.FormRegisterUserDto;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.infra.AuthorityRepository;
import messenger.messenger.auth.user.infra.UserRepository;
import messenger.messenger.auth.oauth.domain.social.ProviderUser;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.token.presentation.dto.LoginDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static messenger.messenger.auth.user.domain.Authorities.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public Long save(Users users) {
        Users saveUsers = userRepository.save(users);
        return saveUsers.getId();
    }

    public Long getUserId(String email) {
        Users users = userRepository.findByEmail(email);

        if (users == null) {
            throw new NotFoundUserException("존재하지 않는 유저입니다.");
        }

        return users.getId();
    }

    public Users findOne(Long id) {
        Optional<Users> optionalUser = userRepository.findById(id);

        return optionalUser.orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 유저 입니다.");
        });
    }

    @Transactional
    public void register(String registrationId, ProviderUser providerUser) {
        Users savedUser = userRepository.save(
                Users.builder()
                        .registrationId(registrationId)
                        .registerId(providerUser.getId())
                        .password(providerUser.getPassword())
                        .email(providerUser.getEmail())
                        .picture(providerUser.getPicture())
                        .build()
        );

        authorityRepository.save(
                Authority.builder().user(savedUser).authorities(ROLE_USER).build()
        );

    }

    @Transactional
    public boolean registerForm(FormRegisterUserDto formRegisterUserDto, PasswordEncoder passwordEncoder) {

        Users findUser = userRepository.findByEmail(formRegisterUserDto.getEmail());

        log.info("findUser = {}", findUser);

        if (findUser == null)  {

            Users user = Users.builder()
                    .username(formRegisterUserDto.getUsername())
                    .password(passwordEncoder.encode(formRegisterUserDto.getPassword()))
                    .email(formRegisterUserDto.getEmail())
                    .build();

            userRepository.save(user);

            authorityRepository.save(Authority.builder().user(user).authorities(ROLE_USER).build());

            return true;
        }
        else {
            return false;
        }
    }

    public Users userLoginCheck(LoginDto loginDto, PasswordEncoder passwordEncoder) {
        return userCheck(loginDto, passwordEncoder);
    }


    /**
     * 이메일로 유저 찾기
     */
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    /**
     * 로그인 정보를 입력 받아 존재하는 회원인지 판단
     */
    private Users userCheck(LoginDto loginDto, PasswordEncoder passwordEncoder) {

        Users findUser = findByEmail(loginDto.getEmail());
        log.info("findUser = {}", findUser);

        if (findUser == null || !passwordEncoder.matches(loginDto.getPassword(), findUser.getPassword())) {
            return null;
        }

        return findUser;
    }


    public Users findUserForAuthentication(Authentication authentication) {
        return findByEmail(authentication.getName());
    }

}
