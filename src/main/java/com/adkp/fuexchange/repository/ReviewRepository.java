package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
}