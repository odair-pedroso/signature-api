package br.com.payment.payments.controller;


import br.com.payment.payments.dto.PaymentDto;
import br.com.payment.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

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



}
