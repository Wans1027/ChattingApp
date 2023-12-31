package Chat.chattingApp.service;

import Chat.chattingApp.entity.Message;
import Chat.chattingApp.redis.RedisMessageCache;
import Chat.chattingApp.repository.MessageRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;

import static Chat.chattingApp.entity.Message.MessageType.ENTER;
import static Chat.chattingApp.entity.Message.MessageType.TALK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {
    @BeforeEach
    public void setUp() {
        stopWatch = new StopWatch();
    }

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageServiceLocalCache messageServiceLocalCache;
    @Autowired
    RedisMessageCache redisMessageCache;

    private StopWatch stopWatch;


    @Test
    void 실행시간() {
        for (int i = 0; i < 30; i++) {
            //DB 저장
            messageRepository.save(new Message(TALK, 1L,1L,"Message"));
            //RedisCache
            messageService.saveMessage(TALK, 1L, "Message", 1L);
            //LocalCache
            messageServiceLocalCache.saveMessage(TALK, 1L, "Message", 1L);
        }

        stopWatch.start("DB");
        messageService.getMessagesInDB(1L);
        stopWatch.stop();

        stopWatch.start("RedisCache");
        messageService.getMessagesInCache(1L);
        stopWatch.stop();

        stopWatch.start("LocalCache");
        messageServiceLocalCache.getMessagesInCache(1L);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());

        redisMessageCache.deleteKey(1L);
    }
}