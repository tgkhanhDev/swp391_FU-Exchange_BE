package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
