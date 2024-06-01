package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.ReportProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportProductRepository extends JpaRepository<ReportProduct, Integer> {
}
