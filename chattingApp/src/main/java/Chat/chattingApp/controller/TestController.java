package Chat.chattingApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController
@Slf4j
public class TestController {
    @Value("${server.port}")
    int port;
    @GetMapping("/test/testing")
    public void test1() {
        log.info("test1 성공");
    }

    @GetMapping("/test/testing2")
    public void test2() {
        log.info("test2 성공");
    }

    @GetMapping("/test")
    public String test3() {
        log.info("test 성공");
        return (port+"포트로 전송됨");
    }

    @GetMapping("/greeting")
    public String greet() {
        log.info("Access /greeting");

        List<String> greetings = Arrays.asList("Hi there", "Greetings", "Salutations");
        Random rand = new Random();

        int randomNum = rand.nextInt(greetings.size());
        return greetings.get(randomNum);
    }
}
