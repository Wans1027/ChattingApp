package Chat.chattingApp.service;

import Chat.chattingApp.dto.MessageDto;
import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MemberRepository;
import Chat.chattingApp.repository.MessageRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService implements DisposableBean {

    private final MessageRepository messageRepository;
    //private static final Queue<Message> messageQueue = new LinkedList<>();
    private static final Map<Long, Queue<Message>> messageMap = new HashMap<>();
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

        Message message = new Message(type, roomId, senderId, detailMessage);
        //messageQueue.add(message);

        if(!messageMap.containsKey(roomId)){
            Queue<Message> q = new LinkedList<>();
            q.add(message);
            messageMap.put(roomId, q);
        }
        else {
            Queue<Message> mQueue = messageMap.get(roomId);
            mQueue.add(message);
            if(mQueue.size() > 50){
                Queue<Message> q = new LinkedList<>();
                for (int i = 0; i < 20; i++) {
                    q.add(mQueue.poll());
                }
                commitMessageQueue(q);
            }
            messageMap.put(roomId, mQueue);

            //큐에 저장된걸 DB 에 언제 넘겨줘야 할까???
            /*
             * 큐가 50개 쌓이면 그중 20개 DB행
             *
             * 메세지를 가져올 때
             * 메모리를 먼저 조회
             * 없다면 DB를 조회
             * 서버를 종료할 때 메모리에 있는 데이터를 DB로 commit
             */
        }
        
        
        
        //if(messageQueue.size() == messageQueueSize) commitMessageQueue();
    }

    public List<Message> getMessages(Long roomId) {
        return messageRepository.findMessageInChattingRoom(roomId);
    }

    public void commitMessageQueue(Queue<Message> messageQueue) {
        //쓰기 지연
        for (int i = 0; i < messageQueue.size(); i++) {
            Message message = messageQueue.poll();
            em.persist(message);

        }
        em.flush();
    }

    @Override
    public void destroy() throws Exception {
        //MessageService Bean 이 삭제될때 즉 서버가 shutDown 될때 동작
        for (Queue<Message> messages : messageMap.values()) {
            commitMessageQueue(messages);
        }
    }
}
