package com.adkp.fuexchange.response;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.VariationDetailDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class OrderDetailResponse {

    private OrdersDTO order;

    private PostProductDTO postProduct;

    private List<OrderDetailResponse> postProductInOrder;

    private List<VariationDetailDTO> variationDetail;

    private int quantity;

    private double priceBought;

    private String firstVariation;

    private String secondVariation;
}
