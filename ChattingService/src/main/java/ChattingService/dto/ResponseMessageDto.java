package ChattingService.dto;

import ChattingService.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageDto {

    Message.MessageType type;
    Long roomId;
    String detailMessage;
    Long senderId;
    String senderName;
    String sendTime;
}
