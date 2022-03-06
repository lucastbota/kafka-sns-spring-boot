package app.messenger.dto;

import app.messenger.model.Payment;

public record PaymentDTO(Long id, Float amount) {

    public static Payment from(PaymentDTO dto) {
        return Payment.builder().id(dto.id()).amount(dto.amount).build();
    }

    public String stringFy() {
        return String.format("%s -- R$ %.2f", this.id(), this.amount);
    }
}
