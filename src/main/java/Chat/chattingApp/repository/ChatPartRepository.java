package Chat.chattingApp.repository;

import Chat.chattingApp.entity.ChatParticipation;
import Chat.chattingApp.entity.ChattingRoom;
import Chat.chattingApp.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatPartRepository extends JpaRepository<ChatParticipation, Long> {

    @Override
    Optional<ChatParticipation> findById(Long aLong);

    @EntityGraph(attributePaths = {"member", "chattingRoom"})
    @Query("select c.member from ChatParticipation c where c.chattingRoom.id = :roomId")
    List<Member> findMemberListInChatRoom(@Param("roomId") Long roomId);

    @EntityGraph(attributePaths = {"member", "chattingRoom"})
    @Query("select c.chattingRoom from ChatParticipation c where c.member.id = :memberId")
    List<ChattingRoom> findChatRoomListByUser(@Param("roomId") Long memberId);


}
