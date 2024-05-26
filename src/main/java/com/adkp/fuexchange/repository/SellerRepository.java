package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Integer> {
    @Query("Select s From Seller s")
    List<Seller> findAllSeller();
}
