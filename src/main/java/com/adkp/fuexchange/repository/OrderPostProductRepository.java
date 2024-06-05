package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.OrderPostProduct;
import com.adkp.fuexchange.pojo.OrderPostProductEmbeddable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPostProductRepository extends JpaRepository<OrderPostProduct, Integer> {
}
