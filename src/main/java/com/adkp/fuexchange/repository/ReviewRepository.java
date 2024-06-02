package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
    @Query("Select rv From Review rv Where rv.orderId.orderId = :orderId")
    List<Review> getReviewByOrderId(@Param("orderId") Integer orderId);

}