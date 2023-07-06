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
    private static final int transactionMessageSize = 20;//트랜잭션에 묶일 메세지 양
    private static final int messagePageableSize = 30; //roomId에 종속된 큐에 보관할 메세지의 양



    public MessageDto messageType(MessageDto message) {
        if (Message.MessageType.ENTER.equals(message.getType())) {
            message.setDetailMessage(message.getSenderId()+"님이 입장하였습니다.");
        }
        saveMessage(message.getType(), message.getRoomId(), message.getDetailMessage(), message.getSenderId());
        return message;
    }



    public void saveMessage(Message.MessageType type, Long roomId, String detailMessage, Long senderId) {

        Message message = new Message(type, roomId, senderId, detailMessage);

        if(!messageMap.containsKey(roomId)){
            //채팅방에 처음쓰는 글이라면 캐시가 없으므로 캐시를 생성
            Queue<Message> q = new LinkedList<>();
            q.add(message);
            messageMap.put(roomId, q);
        }
        else {
            Queue<Message> mQueue = messageMap.get(roomId);
            mQueue.add(message);
            //캐시 쓰기 전략 (Write Back 패턴)
            if (mQueue.size() > transactionMessageSize + messagePageableSize) {
                Queue<Message> q = new LinkedList<>();
                for (int i = 0; i < transactionMessageSize; i++) {
                    q.add(mQueue.poll());
                }
                commitMessageQueue(q);
            }
            messageMap.put(roomId, mQueue);
        }
    }
    public List<Message> getMessages(Long roomId){
        //캐시 읽기 전략 (LookAside 패턴)
        List<Message> messageList = new ArrayList<>();
        if(!messageMap.containsKey(roomId)){
            //Cache Miss
            List<Message> messagesInDB = getMessagesInDB(roomId);
            //DB 에도 없다면 새로 만든 방이므로 빈 리스트를 반환
            if(messagesInDB.isEmpty()) return messageList;
            //DB 에서 가져온 데이터를 Cache 에 적재
            Queue<Message> q = new LinkedList<>(messagesInDB);
            messageMap.put(roomId, q);
            messageList = messagesInDB;
        }
        else {
            //Cache Hit
            messageList = getMessagesInCache(roomId);
        }
        return messageList;
    }

    public List<Message> getMessagesInDB(Long roomId) {
        return messageRepository.findNumberOfMessageInChattingRoomReverse(roomId,messagePageableSize);
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
