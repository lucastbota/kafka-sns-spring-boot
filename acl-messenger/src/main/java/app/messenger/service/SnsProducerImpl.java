package app.messenger.service;

import app.messenger.dto.PaymentDTO;
import com.amazonaws.handlers.AsyncHandler;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SnsProducerImpl implements SnsProducer {
    private final AmazonSNSAsync amazonSNSAsync;
    private final Topic personsEventsTopic;

    @Override
    public void send(PaymentDTO dto) {
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setTopicArn(personsEventsTopic.getTopicArn());
        publishRequest.setMessage(dto.stringFy());
        amazonSNSAsync.publishAsync(publishRequest, new AsyncHandler<>() {
            @Override
            public void onError(Exception e) {
                log.error("Error on publish", e);
            }

            @Override
            public void onSuccess(PublishRequest request, PublishResult publishResult) {
                log.info("Success with messageId: {}", publishResult.getMessageId());
            }
        });
    }
}
