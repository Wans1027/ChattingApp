package Chat.chattingApp.service;

import Chat.chattingApp.dto.MessageDto;
import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MemberRepository;
import Chat.chattingApp.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private static final Queue<Message> messageQueue = new LinkedList<>();
    private final EntityManager em;
    private static final int messageQueueSize = 5;

    public MessageDto messageType(MessageDto message) {
        if (Message.MessageType.ENTER.equals(message.getType())) {
            message.setDetailMessage(message.getSenderId()+"님이 입장하였습니다.");
        }
        saveMessage(message.getType(), message.getRoomId(), message.getDetailMessage(), message.getSenderId());
        return message;
    }


    public void saveMessage(Message.MessageType type, Long roomId, String detailMessage, Long senderId) {

        Message message = new Message(Message.MessageType.TALK, roomId, senderId, detailMessage);
        messageQueue.add(message);
        if(messageQueue.size() == messageQueueSize) commitMessageQueue();
    }

    public List<Message> getMessages(Long roomId) {
        return messageRepository.findMessageInChattingRoom(roomId);
    }

    public void commitMessageQueue() {
        //쓰기 지연
        for (int i = 0; i < messageQueueSize; i++) {
            Message message = messageQueue.poll();
            em.persist(message);

        }
        em.flush();
    }

}
