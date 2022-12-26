package messenger.messenger.business.chatroom.application.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChatRoomMakeReqDto {

    @NotNull
    private String chatroomName;

    @NotNull
    private Integer maxMember;

    @Builder
    public ChatRoomMakeReqDto(String chatroomName, Integer maxMember) {
        this.chatroomName = chatroomName;
        this.maxMember = maxMember;
    }
}
