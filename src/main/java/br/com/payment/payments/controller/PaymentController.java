package br.com.payment.payments.controller;


import br.com.payment.payments.dto.PaymentDto;
import br.com.payment.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @GetMapping
    public Page<PaymentDto> list(@PageableDefault(size=20) Pageable pagination) {
        return service.getAll(pagination);
    }

    @GetMapping("/id")
    public ResponseEntity<PaymentDto> detail (@PathVariable @NotNull Integer id) {
        PaymentDto dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> register (@RequestBody @Valid PaymentDto dto, UriComponentsBuilder uriBuilder) {
        PaymentDto paymentDto = service.createPayment(dto);
        URI address = uriBuilder.path("/payments/{id}").buildAndExpand(paymentDto.getId()).toUri();

        return ResponseEntity.created(address).body(paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update (@PathVariable @NotNull Integer id, @RequestBody @Valid PaymentDto dto) {
        PaymentDto updated = service.updatePayment(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> remove (@PathVariable @NotNull Integer id) {
        service.deletePayment(id);
        return ResponseEntity.noContent().build();
    }



}
