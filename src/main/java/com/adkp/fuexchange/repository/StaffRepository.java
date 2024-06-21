package com.adkp.fuexchange.repository;


import com.adkp.fuexchange.pojo.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    @Query("Select stf From Staff stf " +
            "Where stf.phoneNumber = :phoneNumber")
    Staff findStaffByNumberPhone(@Param("phoneNumber") String phoneNumber);
}
