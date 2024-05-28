package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.pojo.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostProductRepository extends JpaRepository<PostProduct, Integer> {
    @Query("Select pprd from PostProduct pprd")
    List<PostProduct> viewMorePostProduct(Pageable pageable);

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.postProductId =  :postProductId"
    )
    PostProduct getPostProductByPostId(@Param("postProductId") Integer postProductId);

    @Query(
            value = "SELECT * FROM PostProduct pprd " +
                    "WHERE pprd.postProductId LIKE CONCAT('%', :campus, '%') " +
                    "AND pprd.postStatusId LIKE CONCAT('%', :postType, '%') " +
                    "AND pprd.postTypeId LIKE CONCAT('%', :nameProduct, '%')",
            nativeQuery = true
    )
    List<PostProduct> filterPostProduct(
            Pageable pageable, @Param("campus") String campus, @Param("postType") String postType,
            @Param("nameProduct") String nameProduct
    );
}
