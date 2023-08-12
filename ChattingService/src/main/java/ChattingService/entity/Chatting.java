package ChattingService.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

//@Document(collection = "chatting")
@Getter
@ToString
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Chatting {
    //@Id
    private String id;
    private Message.MessageType type;
    private Long roomId;
    private Long senderId;
    private String senderName;
    private String detailMessage;
    private LocalDateTime sendDate;
    private Integer readCNT;
}
