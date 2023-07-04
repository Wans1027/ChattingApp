package Chat.chattingApp.repository;

import Chat.chattingApp.entity.Message;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static Chat.chattingApp.entity.Message.MessageType.TALK;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MessageRepositoryTest {
    @Autowired MessageRepository messageRepository;

    @Test
    void findNumberOfMessageInChattingRoom() {
        for (int i = 0; i < 100; i++) {
            messageRepository.save(new Message(TALK, 1L,1L,"Message"));
        }
        List<Message> messageList = messageRepository.findNumberOfMessageInChattingRoom(1L, 10);
        Collections.reverse(messageList);
        for (int i = 1; i < messageList.size(); i++) {
            boolean result = messageList.get(i).getId() > messageList.get(i-1).getId();
            Assertions.assertThat(result).isTrue();
        }
    }
}