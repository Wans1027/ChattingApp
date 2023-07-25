package Chat.chattingApp.service;

import Chat.chattingApp.entity.ChattingRoom;
import Chat.chattingApp.entity.Member;
import Chat.chattingApp.repository.ChatPartRepository;
import Chat.chattingApp.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatPartService {

    private final ChatPartRepository chatPartRepository;

    /**
     * 한 채팅방에 포함된 유저들
     * 한유저가 보유하고 있는 채팅방 목록
     */
    public List<Member> getMemberListInChattingRoom(Long chattingRoomId) {
        return chatPartRepository.findMemberListInChatRoom(chattingRoomId);
    }

    public List<ChattingRoom> getChattingRoomListInUsers(Long memberId) {
        return chatPartRepository.findChatRoomListByUser(memberId);
    }

}
