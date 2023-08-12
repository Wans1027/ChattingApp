package ChattingService.websocket;

import ChattingService.dto.MessageDto;
import ChattingService.dto.ResponseMessageDto;
import ChattingService.exception.RestException;
import ChattingService.kafka.KafkaConstants;
import ChattingService.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageService {

    private final KafkaTemplate<String, ResponseMessageDto> kafkaTemplate;
    private final MessageService messageService;
    //producer
    public void send(String topic, MessageDto messageDto) {
        log.info("topic : " + topic);
        log.info("send Message : " + messageDto.getDetailMessage());
        try{
            ResponseMessageDto responseMessageDto = messageService.SaveAndChangeToMessageResponseDto(messageDto);
            kafkaTemplate.send(topic,responseMessageDto);
        }catch (Exception e){
            e.printStackTrace();
            throw new RestException(HttpStatus.NOT_ACCEPTABLE,"SAVE Failed");
        }

        //kafkaTemplate.send(topic,messageDto);
    }

    //consumer
    private final SimpMessageSendingOperations sendingOperations;
    private final SimpMessagingTemplate template;
    //@KafkaListener(topics = KafkaConstants.KAFKA_TOPIC, groupId = KafkaConstants.GROUP_ID)
    @KafkaListener(topics = KafkaConstants.KAFKA_TOPIC)
    public void consume(ResponseMessageDto responseMessageDto) throws IOException {
        log.info("Consumed Message : " + responseMessageDto.getDetailMessage());

        //sendingOperations.convertAndSend("/topic/"+messageDto.getRoomId(),messageDto);
        template.convertAndSend("/topic/"+responseMessageDto.getRoomId(),responseMessageDto);

    }


}
