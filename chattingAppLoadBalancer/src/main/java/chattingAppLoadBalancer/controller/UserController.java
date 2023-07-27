package chattingAppLoadBalancer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    @RequestMapping("/hi")
    public Mono<String> hi(@RequestParam(value = "name", defaultValue = "Mary") String name) {
        return loadBalancedWebClientBuilder.build().get().uri("http://chatting/greeting")
                .retrieve().bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }

    @RequestMapping("/send")
    public Mono<String> pub() {
        return loadBalancedWebClientBuilder.build().get().uri("ws://chatting/ws/chat")
                .retrieve().bodyToMono(String.class);
        //.map(greeting -> String.format("%s, %s!", greeting, name));
    }

    @RequestMapping("/message")
    public Mono<String> message() {
        return loadBalancedWebClientBuilder.build().get().uri("ws://chatting/ws/chat")
                .retrieve().bodyToMono(String.class);
    }




    /* RabbitMQ
    http://localhost:15672/
            (기본 계정 guest / guest)*/
}
