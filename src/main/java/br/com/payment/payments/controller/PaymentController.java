package br.com.payment.payments.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.payment.payments.config.PaymentQueue;
import br.com.payment.payments.dto.PaymentDto;
import br.com.payment.payments.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Value("${api.schedule.validate}")
	private String uri;

    @Autowired
    private PaymentService service;
    
    @Autowired
	private PaymentQueue paymentQueue;
	
    @GetMapping
    public Page<PaymentDto> list(@PageableDefault(size=10) Pageable pagination) {
        return service.getAll(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> detail (@PathVariable @NotNull Integer id) {
        PaymentDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    private Boolean sheduleValidate(String scheduleId) {
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> response = restTemplate.getForEntity(uri+"/"+scheduleId, String.class);
    	HttpStatus statusCode = response.getStatusCode();
    	if (statusCode==HttpStatus.OK) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    @PostMapping
    public ResponseEntity<PaymentDto> register (@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriBuilder) {
    	
    	if (sheduleValidate(dto.getScheduleId())) { 
	        PaymentDto paymentDto = service.createPayment(dto);
	        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDto.getId()).toUri();
	        paymentQueue.send(paymentDto.toString());
	        return ResponseEntity.created(address).body(paymentDto);
    	} else {
    		return ResponseEntity.badRequest().build();
    	}
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update (@PathVariable @NotNull Integer id, @RequestBody @Valid PaymentDto dto) {

    	if (sheduleValidate(dto.getScheduleId())) { 
    		PaymentDto updated = service.updatePayment(id, dto);
    		return ResponseEntity.ok(updated);
    	} else {
    		return ResponseEntity.badRequest().build();
    	}

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> remove (@PathVariable @NotNull Integer id) {
        service.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

}
