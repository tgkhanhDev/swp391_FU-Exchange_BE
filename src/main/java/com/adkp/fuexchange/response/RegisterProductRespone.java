package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class RegisterProductRespone {
    private int productId;
    private String sellerId;
    private Category categoryId;
    private double price;
    private boolean productStatus;
    private List<VariationResponse> variationList;

    private ProductDetail productDetail;

    public RegisterProductRespone(int productId, String sellerId,
                                  Category categoryId, double price,
                                  boolean productStatus, List<VariationResponse> variationList,
                                  ProductDetail productDetail) {
        this.productId = productId;
        this.sellerId = sellerId;
        this.categoryId = categoryId;
        this.price = price;
        this.productStatus = productStatus;
        this.variationList = variationList;
        this.productDetail = productDetail;
    }
}
