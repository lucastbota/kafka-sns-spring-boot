package app.messenger.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${employee.topic}")
    private String employeeTopicName;

    @Value(value = "${user.topic}")
    private String userTopicName;

    @Value(value = "${operator.topic}")
    private String operatorTopicName;

    @Bean
    public NewTopic employeeTopic() {
        return TopicBuilder.name(employeeTopicName)
                .partitions(7)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(userTopicName)
                .partitions(7)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic operatorTopic() {
        return TopicBuilder.name(operatorTopicName)
                .partitions(7)
                .replicas(1)
                .build();
    }
}
