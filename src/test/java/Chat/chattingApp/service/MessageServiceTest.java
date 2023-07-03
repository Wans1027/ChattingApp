package Chat.chattingApp.service;

import Chat.chattingApp.entity.Message;
import Chat.chattingApp.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static Chat.chattingApp.entity.Message.MessageType.ENTER;
import static Chat.chattingApp.entity.Message.MessageType.TALK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageServiceTest {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    MessageService messageService;

    @Test
    void saveMessage() {
        for (int i = 0; i < 30; i++) {
            messageRepository.save(new Message(TALK, 1L,1L,"Message"));
        }
        for (int i = 0; i < 30; i++) {
            messageService.saveMessage(TALK, 1L, "Message", 1L);
        }


    }
}