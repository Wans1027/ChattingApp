package ChattingService;

import ChattingService.kafka.KafkaConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.TimeZone;
import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
public class ChattingServiceApplication {

	@PostConstruct
	public void started() {
		String uuid = UUID.randomUUID().toString();
		//KafkaConstants.GROUP_ID = uuid;
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(ChattingServiceApplication.class, args);
	}

}
