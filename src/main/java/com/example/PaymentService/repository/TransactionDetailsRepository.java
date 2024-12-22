package com.example.PaymentService.repository;

import com.example.PaymentService.entity.TranactionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailsRepository extends JpaRepository<TranactionDetails, Long> {
    TranactionDetails findByOrderId(long orderId);
}
