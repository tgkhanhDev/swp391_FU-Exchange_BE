package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    @Query("Select od From Orders od Where od.registeredStudentId.registeredStudentId = :registeredStudentId")
    List<Orders> getOrderByRegisterId(@Param("registeredStudentId") Integer registeredStudentId);
}
