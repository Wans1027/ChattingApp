package Chat.chattingApp.service;

import Chat.chattingApp.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    }
}