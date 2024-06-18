package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.OrderPostProduct;
import com.adkp.fuexchange.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderPostProductRepository extends JpaRepository<OrderPostProduct, Integer> {

    @Query("SELECT odpprd.orderId From OrderPostProduct odpprd WHERE " +
            "odpprd.postProductId.productId.sellerId.sellerId = :sellerId " +
            "AND odpprd.orderId.orderStatusId.orderStatusId = 2")
    List<Orders> getOrdersBySellerId(@Param("sellerId") Integer sellerId);

    @Query("SELECT odpprd FROM OrderPostProduct odpprd " +
            "WHERE odpprd.postProductId.productId.sellerId.sellerId = :sellerId " +
            "AND odpprd.orderId.orderStatusId.orderStatusId = :orderStatusId " +
            "AND odpprd.orderId.orderId = :orderId " +
            "ORDER BY odpprd.orderId.createDate DESC")
    List<OrderPostProduct> getOrdersDetailBySellerIdAndOrderId(
            @Param("sellerId") Integer sellerId, @Param("orderId") Integer orderId, @Param("orderStatusId") Integer orderStatusId
    );

    @Query("SELECT odpprd FROM OrderPostProduct odpprd " +
            "WHERE odpprd.orderId.registeredStudentId.registeredStudentId = :registeredStudentId " +
            "AND odpprd.orderId.orderStatusId.orderStatusId = :orderStatusId " +
            "ORDER BY odpprd.orderId.createDate DESC")
    List<OrderPostProduct> getOrdersDetailByRegisteredStudentId(
            @Param("registeredStudentId") Integer registeredStudentId, @Param("orderStatusId") Integer orderStatusId
    );

}
