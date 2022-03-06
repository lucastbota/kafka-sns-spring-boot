package app.messenger.service;

import app.messenger.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaConsumer {
    private final PaymentService paymentService;
    private final SnsProducer snsProducer;

    @KafkaListener(topics = "${employee.topic}", groupId = "${employee.group.id}",
            concurrency = "1")
    public void listenToEmployee(PaymentDTO dto) {
        persistAndPublish(dto);
    }

    @KafkaListener(topics = "${user.topic}", groupId = "${user.group.id}",
            containerFactory = "userKafkaListenerContainerFactory",
            concurrency = "1")
    public void listenToUsers(PaymentDTO dto) {
        persistAndPublish(dto);
    }

    @KafkaListener(topics = "${operator.topic}", groupId = "${operator.group.id}", containerFactory = "operatorKafkaListenerContainerFactory",
            concurrency = "1")
    public void listenToOperators(PaymentDTO dto) {
        persistAndPublish(dto);
    }

    private void persistAndPublish(PaymentDTO dto) {
        paymentService.save(PaymentDTO.from(dto));
        snsProducer.send(dto);
    }
}
