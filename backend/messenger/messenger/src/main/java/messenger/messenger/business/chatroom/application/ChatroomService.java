package messenger.messenger.business.chatroom.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messenger.messenger.auth.user.domain.Users;
import messenger.messenger.business.chatroom.application.dto.ChatRoomMakeReqDto;
import messenger.messenger.business.chatroom.domain.Chatroom;
import messenger.messenger.business.chatroom.infra.ChatroomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;

    public Long save(ChatRoomMakeReqDto chatRoomMakeReqDto, Users user) {

        Chatroom chatroom = chatroomRepository.save(
                Chatroom.builder()
                        .users(user)
                        .chatroomName(chatRoomMakeReqDto.getChatroomName())
                        .maxMember(chatRoomMakeReqDto.getMaxMember())
                        .build());

        return chatroom.getId();
    }

}
