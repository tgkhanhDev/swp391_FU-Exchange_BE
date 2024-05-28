package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
    @Query("Select t From Transactions t")
    List<Transactions> getAllTransactions();
}
