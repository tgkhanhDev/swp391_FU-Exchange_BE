package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.dto.CartPostDTO;
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
     List<CartPost> getCartPostByStudentId(@Param("studentId") String studentId);

}
