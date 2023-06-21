package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Friend;
import Chat.chattingApp.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f where f.member = :member and f.friendId = :friendId")
    Friend findFriendByMemberAndFriend(@Param("member") Member member, @Param("friendId") Long friendId);

    //친구목록조회
    @Query("select f.friendId from Friend f where f.member = :member")
    List<Long> findFriends(@Param("member") Member member);
}
