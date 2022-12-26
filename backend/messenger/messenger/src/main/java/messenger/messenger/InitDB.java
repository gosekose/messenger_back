package messenger.messenger;


import lombok.RequiredArgsConstructor;
import messenger.messenger.auth.user.domain.Authorities;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.user.infra.AuthorityRepository;
import messenger.messenger.auth.user.infra.UserRepository;
import messenger.messenger.business.school.application.SchoolService;
import messenger.messenger.business.school.application.dto.SchoolSaveDto;
import messenger.messenger.business.school.infra.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final SchoolService schoolService;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstruct() {
        Users admin = Users.builder()
                .email("kkk@naver.com")
                .password(passwordEncoder.encode("ersdsdsdsdqwarr"))
                .username("kkk@naver.com")
                .build();
        userRepository.save(admin);

        authorityRepository.save(
                Authority.builder()
                        .authorities(Authorities.ROLE_USER)
                        .user(admin)
                        .build()
        );

        String[] school = {"서울A대학교", "서울B대학교", "인천A대학교"};
        String[] address = {"서울시 A구", "서울시 B구", "인천시"};
        String[] regisNum = {"123", "124", "125"};

        for (int i=0; i< school.length; i++) {
            schoolService.save(new SchoolSaveDto(school[i], address[i], regisNum[i]));
        }
    }
}