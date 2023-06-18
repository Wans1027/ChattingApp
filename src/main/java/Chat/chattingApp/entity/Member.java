package Chat.chattingApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private long id;
    @NotEmpty
    private String name;
    @Email
    private String email;
    @OneToMany(mappedBy = "member")
    private List<ChatParticipation> chatParticipationList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Friend> friends = new ArrayList<>();

    public Member(String name, String email) {

        this.name = name;
        this.email = email;
    }
}
