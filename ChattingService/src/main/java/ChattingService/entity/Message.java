package ChattingService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatting")
public class Message {
    @Id
    private String id;
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private Long roomId;
    private Long senderId;
    private String senderName;
    private String detailMessage;
    private LocalDateTime sendTime;
    private Integer readCNT = 0;

}