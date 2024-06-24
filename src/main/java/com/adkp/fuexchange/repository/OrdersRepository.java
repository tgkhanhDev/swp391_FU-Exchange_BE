package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    @Query("Select od From Orders od Where od.registeredStudentId.registeredStudentId = :registeredStudentId")
    List<Orders> getOrderByRegisterId(@Param("registeredStudentId") Integer registeredStudentId);

    @Query("Select od From Orders od Where od.orderStatusId.orderStatusId = :orderStatusId AND od.orderId = :orderId " +
            "AND od.orderStatusId.orderStatusId = :orderStatusId")
    Orders getOrderByStatus(@Param("orderId") Integer orderId, @Param("orderStatusId") Integer orderStatusId);

    @Query("Select od From Orders od Where od.orderStatusId.orderStatusId = 1")
    List<Orders> viewCreateOrderRequest();
}
