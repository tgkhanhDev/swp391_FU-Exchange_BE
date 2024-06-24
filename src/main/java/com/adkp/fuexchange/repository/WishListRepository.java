package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Integer> {
    @Query("SELECT wl FROM WishList wl WHERE wl.postProductId.postProductId = :postProductId ")
    List<WishList> getWishListByPostProductID(@Param("postProductId") int postProductId);
    @Query("SELECT wl FROM WishList wl WHERE wl.postProductId.postProductId = :postProductId AND " +
            "wl.registeredStudentId.registeredStudentId = :registeredStudentId")
    WishList checkWistListAvailable(@Param("postProductId") int postProductId,@Param("registeredStudentId") int registeredStudentId);
}
