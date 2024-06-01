package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.OrderPostProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderPostProduct, Integer> {
}
