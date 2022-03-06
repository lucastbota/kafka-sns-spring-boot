package app.messenger.scheduler;

import app.messenger.kafka.config.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataSender {
    private final KafkaProducerService kafkaProducerService;

    @Scheduled(fixedRate = 50000)
    public void send() {
        log.info("Sending");
        kafkaProducerService.send();
        log.info("Stop Sending");
    }
}
