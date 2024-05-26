package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.model.ProductDetail;
import com.adkp.fuexchange.model.Seller;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"productDetailId", "sellerId"})
public class ProductDTO {

    private int productId;

    private ProductDetailDTO detail;

    private List<ProductImageDTO> image;

    private CategoryDTO category;

    private List<VariationDTO> variation;

    private double price;

    private ProductDetail productDetailId;

    private Seller sellerId;
}
