package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.dto.ProductImageDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDTO {

    private int productDetailId;

    private String productName;

    private String description;

    @JsonIgnore
    private List<ProductImageDTO> image;
}
