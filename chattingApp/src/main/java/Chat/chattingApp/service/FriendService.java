package Chat.chattingApp.service;

import Chat.chattingApp.entity.Friend;
import Chat.chattingApp.entity.Member;
import Chat.chattingApp.repository.FriendRepository;
import Chat.chattingApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public void addFriend(Long myId, Long friendId) {
        friendRepository.save(new Friend(memberRepository.findById(myId).orElseThrow(), friendId));
    }

    public void deleteFriend(Long myId, Long friendId) {
        friendRepository.delete
                (friendRepository.findFriendByMemberAndFriend
                        (memberRepository.findById(myId).orElseThrow(), friendId));
    }
    //친구목록 조회
    public List<Member> findFriends(Long myId){
        List<Long> friends = friendRepository.findFriends(memberRepository.findById(myId).orElseThrow());
        List<Member> friendList = new ArrayList<>();
        for (Long friendId : friends) {
            friendList.add(memberRepository.findById(friendId).orElseThrow());
        }
        return friendList;
    }
}
