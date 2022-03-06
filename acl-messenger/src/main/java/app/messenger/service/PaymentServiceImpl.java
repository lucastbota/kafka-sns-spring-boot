package app.messenger.service;

import app.messenger.model.Payment;
import app.messenger.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;

    @Transactional
    @Retryable(value = {SQLException.class, DataIntegrityViolationException.class}, maxAttempts = 2, backoff = @Backoff(delay = 100))
    @Override
    public void save(Payment payment) {
        repository.save(payment);
    }
}
