package Chat.chattingApp.repository;

import Chat.chattingApp.entity.ChatParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatPartRepository extends JpaRepository<ChatParticipation, Long> {

    @Override
    Optional<ChatParticipation> findById(Long aLong);
}
