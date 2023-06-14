package Chat.chattingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
public class ChattingRoom extends TimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private long id;

    @NotEmpty
    private String roomName;
    private String roomState;
    @OneToMany(mappedBy = "chattingRoom")
    private List<ChatParticipation> chatParticipationList;
    @OneToMany(mappedBy = "chattingRoom")
    private List<Message> messages;

}
