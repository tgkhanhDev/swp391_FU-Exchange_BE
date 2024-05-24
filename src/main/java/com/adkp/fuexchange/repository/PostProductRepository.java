package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.PostProduct;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostProductRepository extends JpaRepository<PostProduct, Integer> {
    @Query("Select pprd from PostProduct pprd")
    List<PostProduct> viewMorePostProduct(Pageable pageable);

    @Query("Select pprd from PostProduct pprd Where pprd.campusId.campusId = :campusId")
    List<PostProduct> viewPostProductByCampus(@Param("campusId") int campusId);
}
