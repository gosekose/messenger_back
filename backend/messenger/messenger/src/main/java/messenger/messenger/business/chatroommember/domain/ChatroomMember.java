package messenger.messenger.business.chatroommember.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import messenger.messenger.business.common.BaseEntity;
import messenger.messenger.auth.user.domain.Users;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ChatroomMember extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "chatroom_member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Users member;

}
