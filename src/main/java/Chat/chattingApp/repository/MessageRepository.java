package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select min(m.messageId) from Message m where m.chattingRoomId = :roomId")
    Optional<Long> findMinIdInGroup(@Param("roomId") Long roomId);

}
