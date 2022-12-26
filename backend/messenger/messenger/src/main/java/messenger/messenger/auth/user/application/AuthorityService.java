package messenger.messenger.auth.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.user.domain.Authority;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.auth.user.infra.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    public List<Authority> findAuthorityByUser(Users users) {
        return authorityRepository.findAuthorityByUser(users);
    }


}
