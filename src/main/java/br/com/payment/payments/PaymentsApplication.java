package br.com.payment.payments;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PaymentsApplication {

	@Value("${queue.order.name}")
	private String paymentQueue;

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);
	}

    @Bean
    public Queue queue() {
        return new Queue(paymentQueue, true);
    }	
}
