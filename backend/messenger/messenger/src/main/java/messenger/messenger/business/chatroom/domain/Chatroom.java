package messenger.messenger.business.chatroom.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class Chatroom extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    private String chatroomName;

    private int maxMember;

    private String chatFilePath;

    @Builder
    public Chatroom(Users users, String chatroomName, int maxMember, String chatFilePath) {
        this.users = users;
        this.chatroomName = chatroomName;
        this.maxMember = maxMember;
        this.chatFilePath = chatFilePath;
    }
}
