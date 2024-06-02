package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.RegisteredStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegisteredStudentRepository extends JpaRepository<RegisteredStudent, Integer> {

    @Query("Select rgtstd From RegisteredStudent rgtstd Join Fetch rgtstd.roleId " +
            "Where rgtstd.studentId.studentId = :studentId")
    RegisteredStudent findRegisteredStudentByStudentId(@Param("studentId") String studentId);

//    @Query("SELECT rgtstd.registeredStudentId FROM RegisteredStudent rgtstd WHERE rgtstd.studentId.studentId = :")
//    int getIdByStudentId()
}
