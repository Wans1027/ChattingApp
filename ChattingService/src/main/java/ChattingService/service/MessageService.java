package ChattingService.service;

import ChattingService.dto.MessageDto;
import ChattingService.dto.ResponseMessageDto;
import ChattingService.entity.Message;
import ChattingService.exception.RestException;
import ChattingService.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository eventRepo;


    public Message getEvent(String _id) {
        return eventRepo.findById(_id).orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Not found event"));
    }

    public List<Message> getEventList(Long roomId) {
        return eventRepo.findByRoomId(roomId);
    }

    public Message insertEvent(MessageDto messageDto) {
        return eventRepo.insert(messageDto.changeEntity());
    }

    public ResponseMessageDto SaveAndChangeToMessageResponseDto(MessageDto messageDto){
        Message message = insertEvent(messageDto);
        return ResponseMessageDto.builder()
                .type(message.getType())
                .detailMessage(message.getDetailMessage())
                .senderName(message.getSenderName())
                .roomId(message.getRoomId())
                .sendTime(message.getSendTime().toString())
                .senderId(message.getSenderId())
                .build();

    }

}
