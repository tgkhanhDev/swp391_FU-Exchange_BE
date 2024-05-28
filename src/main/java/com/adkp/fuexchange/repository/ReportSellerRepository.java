package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ReportSeller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportSellerRepository extends JpaRepository<ReportSeller, Integer> {

    @Query("Select rps From ReportSeller rps")
    List<ReportSeller> findAllReportSeller();
}
