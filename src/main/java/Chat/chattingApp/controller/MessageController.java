package Chat.chattingApp.controller;

import Chat.chattingApp.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
        //DB 저장로직
        messageService.sendMessage(request.roomId, request.detailMessage, request.senderId);
    }

    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }


    @Data
    private static class SendMessage{
        Long roomId;
        String detailMessage;
        Long senderId;
    }
}
