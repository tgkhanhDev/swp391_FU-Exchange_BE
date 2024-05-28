package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

    @Query("Select odp From OrderProduct odp")
    List<OrderProduct> findAllOrderProduct();
}
