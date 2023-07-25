package Chat.chattingApp.redis;

import Chat.chattingApp.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

    private RedisMessageCache redisMessageCache;
    private MessageService messageService;

    @Autowired
    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer, RedisMessageCache redisMessageCache, MessageService messageService) {
        super(listenerContainer);
        this.redisMessageCache = redisMessageCache;
        this.messageService = messageService;
    }

    public RedisKeyExpiredListener(@Qualifier("redisMessageListenerContainer") RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        //System.out.println("########## onMessage pattern " + new String(pattern) + " | " + message.toString());
        Long roomId = Long.valueOf(message.toString().substring(4));
        LinkedList<Chat.chattingApp.entity.Message> messages = redisMessageCache.get(roomId);
        Queue<Chat.chattingApp.entity.Message> messageQueue = new LinkedList<>(messages);
        for (Chat.chattingApp.entity.Message message1 : messageQueue) {
            String detailMessage = message1.getDetailMessage();
            System.out.println(detailMessage);
        }
        messageService.commitMessageQueue(messageQueue);
        redisMessageCache.deleteKey(roomId);
    }
}