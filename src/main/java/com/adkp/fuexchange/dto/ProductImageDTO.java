package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class ProductImageDTO {

    private int productImageId;

    @JsonIgnore
    private ProductDetailDTO productDetail;

    private String imageUrl;
}
