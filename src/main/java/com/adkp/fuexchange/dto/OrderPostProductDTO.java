package com.adkp.fuexchange.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderPostProductDTO {

    private OrdersDTO order;

    private PostProductDTO postProduct;

    private VariationDetailDTO variationDetail;

    private int quantity;

    private double priceBought;

    private boolean orderPostProductStatus;
}
