package Chat.chattingApp.dto;

import Chat.chattingApp.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    Message.MessageType type;
    Long roomId;
    String detailMessage;
    Long senderId;
}
