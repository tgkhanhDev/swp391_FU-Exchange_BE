package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.ProductDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"productDetailId"})
public class ProductDTO {

    private int productId;

    private SellerDTO seller;

    private ProductDetailDTO detail;

    private List<ProductImageDTO> image;

    private CategoryDTO category;

    private List<VariationDTO> variation;

    private String price;

    private ProductDetail productDetailId;

}
