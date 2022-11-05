package br.com.payment.payments.service;

import br.com.payment.payments.dto.PaymentDto;
import br.com.payment.payments.model.Payment;
import br.com.payment.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PaymentDto> getAll (Pageable paginacao) {
        return repository
                .findAll(paginacao)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById (Integer id) {
        Payment payment = repository.findById(id)
             .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto createPayment(PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto updatePayment(Integer id, PaymentDto dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public void deletePayment(Integer id) {
        repository.deleteById(id);
    }






















}
