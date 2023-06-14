package Chat.chattingApp.controller;

import Chat.chattingApp.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody SendMessage request){
        messageService.sendMessage(request.roomId, request.detailMessage, request.senderId);
    }


    @Data
    private static class SendMessage{
        Long roomId;
        String detailMessage;
        Long senderId;
    }
}
