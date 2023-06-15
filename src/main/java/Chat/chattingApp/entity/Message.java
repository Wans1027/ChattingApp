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
    private Long messageSequence;
    private String detailMessage;
    private Boolean isCheck;

    public Message(Long chattingRoomId, Long messageSequence, String detailMessage) {
        this.chattingRoomId = chattingRoomId;
        this.messageSequence = messageSequence;
        this.detailMessage = detailMessage;
    }
}
