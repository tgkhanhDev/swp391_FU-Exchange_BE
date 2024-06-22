package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.RegisteredStudent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegisteredStudentRepository extends JpaRepository<RegisteredStudent, Integer> {

    @Query("Select rgtstd From RegisteredStudent rgtstd " +
            "Where rgtstd.studentId.studentId = :studentId")
    RegisteredStudent findRegisteredStudentByStudentId(@Param("studentId") String studentId);
    @Query("SELECT rgtstd FROM RegisteredStudent rgtstd WHERE rgtstd.studentId.firstName LIKE %:nameSearch% " +
            "OR " +
            "rgtstd.studentId.lastName LIKE %:nameSearch%")
    List<RegisteredStudent> findAllRegisteredStudent(Pageable pageable,@Param("nameSearch") String nameSearch);


}
