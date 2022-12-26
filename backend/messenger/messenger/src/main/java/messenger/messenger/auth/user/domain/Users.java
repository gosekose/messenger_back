package messenger.messenger.auth.user.domain;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseEntity;
import messenger.messenger.business.school.domain.School;
import messenger.messenger.business.music.domain.Music;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Users extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;

    private String username;

    private String phoneNum;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "school_id")
    private School school;

    private String profileMessage;

    private String registrationId;
    private String registerId;

    @OneToMany(mappedBy = "user")
    private List<Authority> authorities = new ArrayList<>();

    private String email;
    private String picture;

    @Builder
    public Users(String password, String registrationId, String registerId,
                 String email, String picture, String username) {
        this.username = username;
        this.password = password;
        this.registrationId = registrationId;
        this.registerId = registerId;
        this.email = email;
        this.picture = picture;
    }

    public void addAuthorities(Authority authority) {
        this.authorities.add(authority);
        authority.updateUser(this);
    }
}
