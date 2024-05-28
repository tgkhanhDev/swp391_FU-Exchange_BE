package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.CartPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartPostRepository extends JpaRepository<CartPost, Integer> {
    @Query("Select cp From CartPost cp")
    List<CartPost> findAllCartPost();
}
