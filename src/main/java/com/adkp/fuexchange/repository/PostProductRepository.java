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

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.postTypeId.postTypeId = :postTypeId AND " +
                    "pprd.productId.productDetailId.productName LIKE CONCAT('%', :name, '%')"
    )
    List<PostProduct> filterPostProductByPostTypeAndName(
            Pageable pageable, @Param("postTypeId") Integer postTypeId, @Param("name") String name
    );

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.campusId.campusId = :campusId " +
                    "AND pprd.productId.productDetailId.productName LIKE CONCAT('%', :name, '%')"
    )
    List<PostProduct> filterPostProductByCampusAndName(
            Pageable pageable, @Param("campusId") Integer campusId, @Param("name") String name
    );

    @Query(
            "Select pprd from PostProduct pprd " +
                    "Where pprd.campusId.campusId = :campusId " +
                    "AND pprd.postTypeId.postTypeId = :postTypeId"
    )
    List<PostProduct> filterPostProductByCampusAndPostType(
            Pageable pageable, @Param("campusId") Integer campusId, @Param("postTypeId") Integer postTypeId
    );

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.campusId.campusId = :campusId " +
                    "AND pprd.postTypeId.postTypeId = :postTypeId " +
                    "AND pprd.productId.productDetailId.productName LIKE CONCAT('%', :name, '%')"
    )
    List<PostProduct> filterPostProductByAll(
            Pageable pageable, @Param("campusId") Integer campusId,
            @Param("postTypeId") Integer postTypeId, @Param("name") String name
    );

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.campusId.campusId = :campusId"
    )
    List<PostProduct> filterPostProductByCampus(
            Pageable pageable, @Param("campusId") Integer campusId
    );

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.productId.productDetailId.productName LIKE CONCAT('%', :name, '%')"
    )
    List<PostProduct> filterPostProductByName(
            Pageable pageable, @Param("name") String name
    );

    @Query(
            "SELECT pprd FROM PostProduct pprd " +
                    "WHERE pprd.postTypeId.postTypeId = :postTypeId "
    )
    List<PostProduct> filterPostProductByPostType(
            Pageable pageable, @Param("postTypeId") Integer postTypeId
    );
}
