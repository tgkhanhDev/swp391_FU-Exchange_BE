package com.adkp.fuexchange.response;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.VariationDetailDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDetailResponse {

    private OrdersDTO order;

    private PostProductDTO postProduct;

    private List<VariationDetailDTO> variationDetail;

    private int quantity;

    private double priceBought;

}
