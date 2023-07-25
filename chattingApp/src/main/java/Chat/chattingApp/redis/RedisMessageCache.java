package Chat.chattingApp.redis;

import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisMessageCache {
    private final RedisTemplate<String, LinkedList<Message>> redisTemplate;
    private final RedisTemplate<String, String> redisTemplateName;
    private final MessageRepository messageRepository;

    @Value("${spring.redis.expire}")
    private int expireTime;

    public void put(Long roomId, Queue<Message> messageQueue){
        redisTemplate.opsForValue().set(roomId.toString(), new LinkedList<>(messageQueue));
        redisTemplateName.opsForValue().set("room"+roomId, "");
        redisTemplateName.expire("room"+roomId.toString(), expireTime, TimeUnit.SECONDS);
    }

    public boolean containsKey(Long roomId){
        return Boolean.TRUE.equals(redisTemplate.hasKey(roomId.toString()));
    }
    public LinkedList<Message> get(Long roomId){

        return redisTemplate.opsForValue().get(roomId.toString());
    }

    public Queue<Message> values(){
        return get(Long.valueOf(Objects.requireNonNull(redisTemplate.randomKey())));
    }

    public void deleteKey(Long roomId){
        redisTemplate.delete(roomId.toString());
    }
    public void deleteAll(){
        redisTemplate.discard();
    }

}
