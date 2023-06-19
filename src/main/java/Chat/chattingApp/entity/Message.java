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
    private Long messageSequence;
    private Long senderId;
    private String detailMessage;
    private Boolean isCheck;

    public Message(Long chattingRoomId, Long messageSequence, Long senderId,String detailMessage) {
        this.chattingRoomId = chattingRoomId;
        this.messageSequence = messageSequence;
        this.senderId = senderId;
        this.detailMessage = detailMessage;
    }
}
