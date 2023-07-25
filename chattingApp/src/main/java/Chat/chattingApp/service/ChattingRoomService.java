package Chat.chattingApp.service;

import Chat.chattingApp.entity.ChatParticipation;
import Chat.chattingApp.entity.ChattingRoom;
import Chat.chattingApp.repository.ChatPartRepository;
import Chat.chattingApp.repository.ChattingRoomRepository;
import Chat.chattingApp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final ChatPartRepository chatPartRepository;
    private final MemberRepository memberRepository;

    /**
     * 채팅룸을 만들때 요구사항
     *
     * 1:1 채팅일시
     * 채팅룸을 처음 만들면 2명의 유저가 동시에 참여함
     *
     * 그룹일 경우 초대가 되어야됨
     * 채팅을 만들면 만든 유저가 혼자 있음
     * 방의 최대인원은 20명
     *
     */

    public Long createChattingRoom(String roomName, Long memberId) {
        ChattingRoom chattingRoom = new ChattingRoom(roomName, "Active");
        ChattingRoom savedChattingRoom = chattingRoomRepository.save(chattingRoom);
        ChatParticipation chatParticipation = new ChatParticipation(memberRepository.findById(memberId).get(), savedChattingRoom);
        chatPartRepository.save(chatParticipation);
        return savedChattingRoom.getId();
    }

    public void inviteChattingRoom(Long chatRoomId, Long memberId) {
        ChatParticipation chatParticipation = new ChatParticipation(memberRepository.findById(memberId).get(), chattingRoomRepository.findById(chatRoomId).get());
        chatPartRepository.save(chatParticipation);
    }

}
