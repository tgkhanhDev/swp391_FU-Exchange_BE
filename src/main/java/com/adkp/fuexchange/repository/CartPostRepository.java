package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.CartPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartPostRepository extends JpaRepository<CartPost, Integer> {
}
