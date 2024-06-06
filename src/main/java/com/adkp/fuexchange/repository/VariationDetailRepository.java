package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.pojo.Product;
import com.adkp.fuexchange.pojo.VariationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VariationDetailRepository  extends JpaRepository<VariationDetail, Integer> {
    @Query("SELECT vd.variationId.productId FROM VariationDetail vd WHERE vd.variationDetailId = :variationDetailId")
    Product getProductByVariationDetail(@Param("variationDetailId") int variationDetailId);


}
