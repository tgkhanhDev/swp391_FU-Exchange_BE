package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.PostProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostProductRepository extends JpaRepository<PostProduct, Integer> {

    @Query("Select pp From PostProduct pp")
    List<PostProduct> findAllPostProduct();
}
