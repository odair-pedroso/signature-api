package br.com.payment.payments.repository;

import br.com.payment.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository <Payment, Long>{
}
