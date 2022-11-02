package br.com.payment.payments.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotBlank
    @Size(max=20)
    private String paymentType;
    @NotBlank
    @Size(max=19)
    private String paymentCode;
    @NotNull
    private Integer scheduleId;
    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
