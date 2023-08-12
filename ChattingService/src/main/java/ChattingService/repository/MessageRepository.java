package ChattingService.repository;

import ChattingService.entity.Chatting;
import ChattingService.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByRoomId(Long roomId);
}
