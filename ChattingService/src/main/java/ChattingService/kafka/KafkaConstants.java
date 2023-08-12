package ChattingService.kafka;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

// Kafka 에서 통신할 내용

public class KafkaConstants {
    private static String name = UUID.randomUUID().toString();
    public static final String KAFKA_TOPIC = "test-chat";
    public static final String GROUP_ID = "name";
    public static final String KAFKA_BROKER = "localhost:9092";
    public static List<Integer> partitionList;
}
