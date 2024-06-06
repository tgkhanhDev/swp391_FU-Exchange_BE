package com.adkp.fuexchange.request;

import com.adkp.fuexchange.pojo.ProductDetail;
import com.adkp.fuexchange.pojo.Variation;
import lombok.Data;

import java.util.List;
@Data
public class UpdateInformationProductRequest {
    private Integer productID;

    private ProductDetail productDetailId;

    private Integer categoryId;

    private double price;

    private List<Variation> variationId;
}
