package Chat.chattingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@Getter
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


    public ChattingRoom(String roomName, String roomState) {
        this.roomName = roomName;
        this.roomState = roomState;
    }
}
