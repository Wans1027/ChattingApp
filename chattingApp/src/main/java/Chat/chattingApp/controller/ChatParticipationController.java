package Chat.chattingApp.controller;

import Chat.chattingApp.entity.ChattingRoom;
import Chat.chattingApp.entity.Member;
import Chat.chattingApp.service.ChatPartService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatParticipationController {

    private final ChatPartService chatPartService;

    @GetMapping("/getMemberNameList")
    public List<String> getMemberListByGroup(Long roomId) {
        //채팅방 pk를 넣으면 해당방의 유저들의 이름을 반환
        List<Member> mList = chatPartService.getMemberListInChattingRoom(roomId);
        return mList.stream().map(Member::getName).toList();
    }

    @GetMapping("/getChattingRoomList")
    public List<ChattingRoomDto> getChattingRoomList(Long memberId) {
        List<ChattingRoom> roomList = chatPartService.getChattingRoomListInUsers(memberId);
        return roomList.stream().map(r -> new ChattingRoomDto(r.getId(), r.getRoomName())).toList();
    }


    @Data
    @AllArgsConstructor
    private static class ChattingRoomDto {
        Long id;
        String roomName;

    }
}
