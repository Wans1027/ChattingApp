package Chat.chattingApp.controller;

import Chat.chattingApp.dto.MessageDto;
import Chat.chattingApp.entity.Message;
import Chat.chattingApp.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessageSendingOperations sendingOperations;


    @MessageMapping("/message")
    public void sendMessage(MessageDto message) {
        MessageDto messageDto = messageService.messageType(message);
        sendingOperations.convertAndSend("/topic/chat/room/"+messageDto.getRoomId(),message);
    }

    @GetMapping("/message/{chattingRoomId}")
    public List<MessageDto> loadMessagesChattingRoom(@PathVariable long chattingRoomId){
        List<Message> messages = messageService.getMessages(chattingRoomId);
        return messages.stream().map(message -> new MessageDto(message.getType(), message.getChattingRoomId(), message.getDetailMessage(), message.getSenderId())).toList();
    }

}
