package Chat.chattingApp.service;

import Chat.chattingApp.repository.ChatPartRepository;
import Chat.chattingApp.repository.ChattingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatPartService {

    private final ChatPartRepository chatPartRepository;

    /**
     * 채팅방에 누가있는지
     * 한유저가 보유하고 있는 채팅방 목록
     */
    public void getListInChattingRoom(Long chattingRoomId) {

    }

}
