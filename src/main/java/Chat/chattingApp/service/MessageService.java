package Chat.chattingApp.service;

import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MemberRepository;
import Chat.chattingApp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void sendMessage(Long roomId, String detailMessage, Long senderId) {
        Long messageId = messageRepository.findMinIdInGroup(roomId).orElse(-1L);
        Message message = new Message(roomId, messageId + 1, detailMessage);
        messageRepository.save(message);
    }

}
