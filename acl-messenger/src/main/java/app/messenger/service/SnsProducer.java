package app.messenger.service;

import app.messenger.dto.PaymentDTO;

public interface SnsProducer {
    void send(PaymentDTO dto);
}
