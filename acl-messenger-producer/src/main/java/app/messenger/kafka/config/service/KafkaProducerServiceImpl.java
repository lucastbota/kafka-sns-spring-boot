package app.messenger.kafka.config.service;

import app.messenger.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;


@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, PaymentDTO> kafkaTemplate;

    @Value(value = "${employee.topic}")
    private String employeeTopicName;
    @Value(value = "${user.topic}")
    private String userTopicName;
    @Value(value = "${operator.topic}")
    private String operatorTopicName;

    @Override
    public void send() {
        long initialTime = System.currentTimeMillis();
        List<Thread> operatorOperations = getOperatorOperation();
        List<Thread> employeeOperations = getEmployeesOperation();
        List<Thread> userOperations = getUserOperation();

        operatorOperations.forEach(startAction());
        employeeOperations.forEach(startAction());
        userOperations.forEach(startAction());

        operatorOperations.forEach(joinAction());
        employeeOperations.forEach(joinAction());
        userOperations.forEach(joinAction());

        long endTime = System.currentTimeMillis();
        log.info("Elapsed Time: {}", (endTime - initialTime));
        log.info("Started at Time: {}", initialTime);
        log.info("Finished at Time: {}", endTime);
    }

    private Consumer<Thread> startAction() {
        return t -> t.start();
    }

    private Consumer<Thread> joinAction() {
        return t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    private List<Thread> getOperatorOperation() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var payment = new PaymentDTO((long) i, new Random().nextFloat(1000f));
            Runnable addOperator = () -> kafkaTemplate.send(operatorTopicName, payment);
            threads.add(new Thread(addOperator));
        }
        return threads;
    }

    private List<Thread> getEmployeesOperation() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var payment = new PaymentDTO((long) i, new Random().nextFloat(2000f));
            Runnable addEmployee = () -> kafkaTemplate.send(employeeTopicName, payment);
            threads.add(new Thread(addEmployee));
        }
        return threads;
    }

    private List<Thread> getUserOperation() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            var payment = new PaymentDTO((long) i, new Random().nextFloat(1500f));
            Runnable userOperator = () -> kafkaTemplate.send(userTopicName, payment);
            threads.add(new Thread(userOperator));
        }
        return threads;
    }
}
