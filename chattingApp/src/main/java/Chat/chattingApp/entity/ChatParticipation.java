package Chat.chattingApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Entity
@AllArgsConstructor
@Getter
public class ChatParticipation {
    @Id
    @GeneratedValue
    @Column(name = "chatPart_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chattingRoom_id")
    private ChattingRoom chattingRoom;

    public ChatParticipation(Member member, ChattingRoom chattingRoom) {
        this.member = member;
        this.chattingRoom = chattingRoom;
    }
}
