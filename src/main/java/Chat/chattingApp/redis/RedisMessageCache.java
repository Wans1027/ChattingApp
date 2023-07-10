package Chat.chattingApp.redis;

import Chat.chattingApp.entity.Message;
import lombok.RequiredArgsConstructor;
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

    public void put(Long roomId, LinkedList<Message> messageQueue){
        redisTemplate.opsForValue().set(roomId.toString(), messageQueue);
        redisTemplate.expire(roomId.toString(), 60, TimeUnit.MINUTES);
    }

    public boolean containsKey(Long roomId){
        return Boolean.TRUE.equals(redisTemplate.hasKey(roomId.toString()));
    }
    public LinkedList<Message> get(Long roomId){
        return redisTemplate.opsForValue().get(roomId.toString());
    }

    public Queue<Message> getValues(){
        return get(Long.valueOf(Objects.requireNonNull(redisTemplate.randomKey())));
    }

}
