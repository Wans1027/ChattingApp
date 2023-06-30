package Chat.chattingApp.service;

import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MemberRepository;
import Chat.chattingApp.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private static final Queue<Message> messageQueue = new LinkedList<>();
    private final EntityManager em;
    private static final int messageQueueSize = 5;


    public void sendMessage(Message.MessageType type, Long roomId, String detailMessage, Long senderId) {

        Message message = new Message(Message.MessageType.TALK, roomId, senderId, detailMessage);
        messageQueue.add(message);
        if(messageQueue.size() == messageQueueSize) commitMessageQueue();
        //messageRepository.save(message);
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
