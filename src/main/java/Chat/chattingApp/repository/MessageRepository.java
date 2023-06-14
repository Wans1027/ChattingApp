package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
