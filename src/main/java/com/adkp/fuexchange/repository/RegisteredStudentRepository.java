package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.model.RegisteredStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegisteredStudentRepository extends JpaRepository<RegisteredStudent, Integer> {
    @Query("Select rs From RegisteredStudent rs")
    List<RegisteredStudent> findAllRegisteredStudent();

}
