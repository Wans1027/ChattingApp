package ChattingService.kafka;

import ChattingService.dto.MessageDto;
import ChattingService.dto.ResponseMessageDto;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerAwareRebalanceListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EnableKafka
@Configuration
@Slf4j
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ResponseMessageDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ResponseMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        ContainerProperties prop = factory.getContainerProperties();
        prop.setConsumerRebalanceListener(rebalanceListener());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, ResponseMessageDto> consumerFactory() {
        JsonDeserializer<ResponseMessageDto> deserializer = new JsonDeserializer<>(ResponseMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
                .put("group.id", KafkaConstants.GROUP_ID)
                .build();

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConsumerAwareRebalanceListener rebalanceListener() {
        return new ConsumerAwareRebalanceListener() {
            @Override
            public void onPartitionsAssigned(Consumer<?, ?> consumer, Collection<TopicPartition> partitions) {
                // here partitions
                List<Integer> partList = new ArrayList<>();
                for (TopicPartition partition : partitions) {
                    int partition1 = partition.partition();
                    partList.add(partition1);
                    log.info("사용중인파티션:{}", partition1);
                }
                KafkaConstants.partitionList = partList;
            }
        };
    }

}