package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("Select prd from Product prd")
    List<Product> topProduct(Pageable pageable);




}
