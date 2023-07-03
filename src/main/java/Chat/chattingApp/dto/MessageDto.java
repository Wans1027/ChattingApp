package Chat.chattingApp.dto;

import Chat.chattingApp.entity.Message;
import lombok.Data;

@Data
public class MessageDto {
    Message.MessageType type;
    Long roomId;
    String detailMessage;
    Long senderId;
}
