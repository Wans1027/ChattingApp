package Chat.chattingApp.dto;

import Chat.chattingApp.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    Message.MessageType type;
    Long roomId;
    String detailMessage;
    Long senderId;
}
