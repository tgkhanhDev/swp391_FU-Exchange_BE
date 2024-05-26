package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
    @Query("Select rv From Review rv")
    List<Review> findAllReview();

}