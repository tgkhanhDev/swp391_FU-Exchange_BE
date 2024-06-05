package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("Select prd from Product prd")
    List<Product> topProduct(Pageable pageable);

    @Query("SELECT prd FROM Product prd WHERE prd.sellerId.sellerId = :sellerId " +
            "AND prd.productDetailId.productName LIKE %:productName%")
    List<Product>filterSellerProduct(@Param("sellerId") int sellerID,@Param("productName") String productName, Pageable pageable );


    @Query("SELECT prd FROM Product prd WHERE prd.productId = :productId ")
    List<Product>getProductByProductID(@Param("productId") int productID );

}
