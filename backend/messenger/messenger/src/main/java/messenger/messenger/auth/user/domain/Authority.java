package messenger.messenger.auth.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Enumerated(STRING)
    private Authorities authorities;

    @Builder
    public Authority(Users user, Authorities authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    public void updateUser(Users user) {
        this.user = user;
    }

}
