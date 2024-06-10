package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.CartPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartPostRepository extends JpaRepository<CartPost, Integer> {
    @Query("Select cp From CartPost cp")
    List<CartPost> findAllCartPost();
  
     @Query("SELECT cp FROM CartPost cp " +
            "WHERE cp.cartId.registeredStudentId.studentId.studentId = :studentId ")
     List<CartPost> getCartProductByStudentId(@Param("studentId") String studentId);


    @Query("SELECT cp FROM CartPost cp " +
            "WHERE cp.cartId.cartId = :cartId AND cp.postProductId.postProductId = :postProductId AND cp.variationDetailId.variationDetailId = :variationDetailId ")
    CartPost getCartPostById(@Param("cartId") int cartId, @Param("postProductId") int postProductId, @Param("variationDetailId") int variationDetailId );

}
