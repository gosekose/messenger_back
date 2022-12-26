package messenger.messenger.business.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.common.BaseEntity;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Friend extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "req_user_id")
    private Users reqUser;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "res_user_id")
    private Users resUser;

    @Enumerated(EnumType.STRING)
    private JoinStatus joinStatus;

    @Builder
    public Friend(Users reqUser, Users resUser, JoinStatus joinStatus) {
        this.reqUser = reqUser;
        this.resUser = resUser;
        this.joinStatus = joinStatus;
    }

    public void updateJoinStatus(JoinStatus joinStatus) {
        this.joinStatus = joinStatus;
    }
}
