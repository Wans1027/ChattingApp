package chattingAppLoadBalancer.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class UserController {
    private final WebClient.Builder loadBalancedWebClientBuilder;
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public UserController(WebClient.Builder webClientBuilder,
                           ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.loadBalancedWebClientBuilder = webClientBuilder;
        this.lbFunction = lbFunction;
    }
    @RequestMapping("/hi")
    public Mono<String> hi(@RequestParam(value = "name", defaultValue = "Mary") String name) {
        return loadBalancedWebClientBuilder.build().get().uri("http://chatting/greeting")
                .retrieve().bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }

    @GetMapping("/request/{uriTail}")
    public Mono<String> test(@PathVariable String uriTail) {
        log.info("Address http://localhost:0000/request/{}",uriTail);
        return loadBalancedWebClientBuilder.build().get().uri("http://chatting/" + uriTail)
                .retrieve().bodyToMono(String.class);

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
