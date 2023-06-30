package Chat.chattingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class Message extends TimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private Long chattingRoomId;
    private Long senderId;
    private String detailMessage;
    private Boolean isCheck;



    public Message(MessageType type, Long chattingRoomId, Long senderId, String detailMessage) {
        this.type = type;
        this.chattingRoomId = chattingRoomId;
        this.senderId = senderId;
        this.detailMessage = detailMessage;
    }
}
