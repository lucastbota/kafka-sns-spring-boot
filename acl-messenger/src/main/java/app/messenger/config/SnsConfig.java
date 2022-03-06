package app.messenger.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnsConfig {
    @Value("${aws.region}")
    private String awsRegion;
    @Value("${aws.sns.topic.person.events.arn}")
    private String personsEventTopic;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.accessSecret}")
    private String secretKey;
    @Value("${aws.endpoint}")
    private String awsEndpoint;

    @Bean
    public AmazonSNSAsync amazonSNSAsync() {
        return AmazonSNSAsyncClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean(name = "personsEventsTopic")
    public Topic snsPersonsEventsTopic() {
        return new Topic().withTopicArn(personsEventTopic);
    }
}
