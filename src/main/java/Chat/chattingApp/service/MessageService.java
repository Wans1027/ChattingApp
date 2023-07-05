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
    private static final Map<Long, Queue<Message>> messageMap = new HashMap<>();
    private final EntityManager em;

    public MessageDto messageType(MessageDto message) {
        if (Message.MessageType.ENTER.equals(message.getType())) {
            message.setDetailMessage(message.getSenderId()+"님이 입장하였습니다.");
        }
        saveMessage(message.getType(), message.getRoomId(), message.getDetailMessage(), message.getSenderId());
        return message;
    }

    /**
     * 상황가정
     * 채팅방에 들어왔는데 캐시가 없다?
     * DB 에서 꺼내 캐시에 저장
     *
     * 처음채팅방을 만들면
     * 꺼내올 필요가 없지???
     */


    public void saveMessage(Message.MessageType type, Long roomId, String detailMessage, Long senderId) {

        Message message = new Message(type, roomId, senderId, detailMessage);

        if(!messageMap.containsKey(roomId)){
            Queue<Message> q = new LinkedList<>();
            q.add(message);
            messageMap.put(roomId, q);
            //캐시가 없다면 DB 에서 가져와 캐시에 저장하는 로직 필요
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
        }
    }
    public List<Message> getMessages(Long roomId){
        List<Message> messageList;
        if(!messageMap.containsKey(roomId)){
            //캐시에 없다면 DB 에서 불러와 캐시에 저장 (Cache Miss)
            List<Message> messagesInDB = getMessagesInDB(roomId);
            Queue<Message> q = new LinkedList<>(messagesInDB);
            messageMap.put(roomId, q);
            messageList = messagesInDB;
        }
        else {
            //Cache Hit
            messageList = getMessagesInCache(roomId);
            Collections.reverse(messageList);
        }
        return messageList;
    }

    public List<Message> getMessagesInDB(Long roomId) {
        return messageRepository.findMessageInChattingRoom(roomId);
    }

    public List<Message> getMessagesInCache(Long roomId){
        return messageMap.get(roomId).stream().toList();
    }

    private void commitMessageQueue(Queue<Message> messageQueue) {
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
