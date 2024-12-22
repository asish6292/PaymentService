package com.example.PaymentService.service;

import com.example.PaymentService.entity.TranactionDetails;
import com.example.PaymentService.model.PaymentMode;
import com.example.PaymentService.model.PaymentRequest;
import com.example.PaymentService.model.PaymentResponse;
import com.example.PaymentService.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details:{}", paymentRequest);
        TranactionDetails tranactionDetails = TranactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();
        transactionDetailsRepository.save(tranactionDetails);
        log.info("Payment completed with id: {}", tranactionDetails.getId());
        return tranactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details for orderId:{}", orderId);
        TranactionDetails tranactionDetails = transactionDetailsRepository.findByOrderId(orderId);
        return PaymentResponse.builder()
                .paymentId(tranactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(tranactionDetails.getPaymentMode()))
                .paymentDate(tranactionDetails.getPaymentDate())
                .orderId(tranactionDetails.getOrderId())
                .status(tranactionDetails.getPaymentStatus())
                .amount(tranactionDetails.getAmount())
                .build();
    }
}
