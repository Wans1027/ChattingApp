package Chat.chattingApp.repository;

import Chat.chattingApp.entity.ChattingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {

    @Override
    Optional<ChattingRoom> findById(Long aLong);
}
