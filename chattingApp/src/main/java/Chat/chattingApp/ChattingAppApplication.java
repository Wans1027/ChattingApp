package Chat.chattingApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
public class ChattingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChattingAppApplication.class, args);
	}

}
