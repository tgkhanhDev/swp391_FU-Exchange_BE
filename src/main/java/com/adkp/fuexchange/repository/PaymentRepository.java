package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("Select pm From Payment pm")
    List<Payment> findAllPayment();
}
