package Chat.chattingApp.redis;

import Chat.chattingApp.entity.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.Queue;

import static Chat.chattingApp.entity.Message.MessageType.TALK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RedisMessageCacheTest {

    @Autowired RedisMessageCache redisMessageCache;

    @Test
    void put() throws InterruptedException {
        Message message = new Message(TALK, 1L,1L,"Message");
        Queue<Message> q = new LinkedList<>();
        q.add(message);
        q.add(new Message(TALK, 1L,1L,"Message2"));
        q.add(new Message(TALK, 1L,1L,"Message3"));

        redisMessageCache.put(1L, q);
        LinkedList<Message> messages = redisMessageCache.get(1L);

        Thread.sleep(6000);
        Queue<Message> q1 = new LinkedList<>();
        q1.add(message);
        q1.add(new Message(TALK, 1L,1L,"Message4"));
        q1.add(new Message(TALK, 1L,1L,"Message5"));
        redisMessageCache.put(1L, q1);
    }
}