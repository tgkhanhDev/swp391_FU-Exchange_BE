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
            value = "SELECT pprd.* FROM PostProduct pprd " +
                    "JOIN Product p ON pprd.productId = p.productId " +
                    "JOIN ProductDetail pd ON p.productDetailId = pd.productDetailId " +
                    "WHERE pprd.campusId LIKE CONCAT('%', :campus, '%') " +
                    "AND pprd.postTypeId LIKE CONCAT('%', :postType, '%') " +
                    "AND pd.productName LIKE CONCAT('%', :name, '%')",
            nativeQuery = true
    )
    List<PostProduct> filterPostProduct(
            Pageable pageable, @Param("campus") String campus, @Param("postType") String postType,
            @Param("name") String nameProduct
    );
}
