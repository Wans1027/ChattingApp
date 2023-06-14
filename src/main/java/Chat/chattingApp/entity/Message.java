package Chat.chattingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@AllArgsConstructor
public class Message extends TimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;
    private Long chattingRoomId;
    private Long messageId;
    private String detailMessage;
    private Boolean isCheck;

    public Message(Long chattingRoomId, Long messageId, String detailMessage) {
        this.chattingRoomId = chattingRoomId;
        this.messageId = messageId;
        this.detailMessage = detailMessage;
    }
}
