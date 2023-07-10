package Chat.chattingApp.redis;

import Chat.chattingApp.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpirationListener implements MessageListener {
    private final MessageService messageService;
    private final RedisMessageCache messageMap;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //서버가 키를 삭제할 때 동작
        log.info("{} Expired",message.toString());
        Queue<Chat.chattingApp.entity.Message> messageQueue = messageMap.get(Long.valueOf(message.toString()));
        messageService.commitMessageQueue(messageQueue);
    }
}
