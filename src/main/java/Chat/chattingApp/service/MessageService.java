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

    public void sendMessage(Message.MessageType type, Long roomId, String detailMessage, Long senderId) {
        Long messageId = messageRepository.findMinIdInGroup(roomId).orElse(0L);
        Message message = new Message(Message.MessageType.TALK, roomId, messageId + 1, senderId, detailMessage);
        messageRepository.save(message);
    }

}
