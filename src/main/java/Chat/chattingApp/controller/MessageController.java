package Chat.chattingApp.controller;

import Chat.chattingApp.entity.Message;
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
    private final SimpMessageSendingOperations sendingOperations;

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody SendMessage request){
        //DB 저장로직
        messageService.sendMessage(Message.MessageType.TALK,request.roomId, request.detailMessage, request.senderId);
    }

    @MessageMapping("/message")
    public void enter(SendMessage message) {
        if (Message.MessageType.ENTER.equals(message.getType())) {
            message.setDetailMessage(message.getSenderId()+"님이 입장하였습니다.");
            messageService.sendMessage(Message.MessageType.ENTER,message.roomId, message.detailMessage, message.senderId);
        }
        else messageService.sendMessage(Message.MessageType.TALK,message.roomId, message.detailMessage, message.senderId);
        log.info("roomID = {}", message.getRoomId());
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }


    @Data
    private static class SendMessage{
        Message.MessageType type;
        Long roomId;
        String detailMessage;
        Long senderId;
    }

    @Data
    private static class MessageDto{
        Message.MessageType type;
        Long roomId;
        String detailMessage;
        Long senderId;
    }
}
