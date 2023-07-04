package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

//    @Query("select max(m.messageSequence) from Message m where m.chattingRoomId = :roomId")
//    Optional<Long> findMaxIdInGroup(@Param("roomId") Long roomId);

    @Query("select m from Message m where m.chattingRoomId = :roomId")
    List<Message> findMessageInChattingRoom(@Param("roomId") Long roomId);

    @Query("select n from (select m from Message m where m.chattingRoomId = :roomId order by m.id desc limit :num) n order by n.id") //sub 쿼리
    List<Message> findNumberOfMessageInChattingRoom(@Param("roomId") Long roomId, @Param("num") int num);
}
