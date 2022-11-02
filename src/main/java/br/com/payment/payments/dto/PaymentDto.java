package br.com.payment.payments.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentDto {

    private Long id;
    private BigDecimal amount;
    private String paymentType;
    private String paymentCode;
    private String scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
