package Chat.chattingApp.controller;

import Chat.chattingApp.dto.MessageDto;
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


    @MessageMapping("/message")
    public void sendMessage(MessageDto message) {
        MessageDto messageDto = messageService.messageType(message);
        log.info("roomID = {}", message.getRoomId());
        sendingOperations.convertAndSend("/topic/chat/room/"+messageDto.getRoomId(),message);
    }

}
