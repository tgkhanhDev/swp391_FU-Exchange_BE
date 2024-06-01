package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {
}
