package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.PostProduct;
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
                    "WHERE pprd.postProductId =  :postProductId " +
                    "AND pprd.postStatusId.postStatusId = 4"
    )
    PostProduct getPostProductByPostId(@Param("postProductId") Integer postProductId);

    @Query(
            value = "SELECT pprd.* FROM PostProduct pprd " +
                    "JOIN Product p ON pprd.productId = p.productId " +
                    "JOIN ProductDetail pd ON p.productDetailId = pd.productDetailId " +
                    "WHERE pprd.postStatusId = 4 " +
                    "AND pprd.campusId LIKE CONCAT('%', :campus, '%') " +
                    "AND pprd.postTypeId LIKE CONCAT('%', :postType, '%') " +
                    "AND pd.productName LIKE CONCAT('%', :name, '%') " +
                    "AND p.categoryId LIKE CONCAT('%', :category, '%')",
            nativeQuery = true
    )
    List<PostProduct> filterPostProduct(
            Pageable pageable, @Param("campus") String campus, @Param("postType") String postType,
            @Param("name") String name, @Param("category") String categoryId
    );

    @Query("Select pprd From PostProduct pprd Where pprd.postStatusId.postStatusId = 2")
    List<PostProduct> viewCreateOrderRequest();
}
