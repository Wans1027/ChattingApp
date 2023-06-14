package Chat.chattingApp.controller;

import Chat.chattingApp.service.ChattingRoomService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChattingRoomController {

    private final ChattingRoomService chattingRoomService;

    @PostMapping("/create_room")
    public Long createChattingRoom(@RequestBody @Valid createRoom request) {
        return chattingRoomService.createChattingRoom(request.roomName, request.memberId);
    }

    @PostMapping("/invite_room")
    public void inviteChattingRoom(@RequestBody @Valid inviteRoom request) {
        chattingRoomService.inviteChattingRoom(request.roomId, request.memberId);
    }

    @Data
    private static class createRoom {
        String roomName;
        Long memberId;
    }

    @Data
    private static class inviteRoom {
        Long roomId;
        Long memberId;
    }
}
