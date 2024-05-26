package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

    @Query("Select st From Staff st")
    List<Staff> getAllStaff();
}
