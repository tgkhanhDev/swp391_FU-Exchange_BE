package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.Product;
import com.adkp.fuexchange.pojo.Variation;
import lombok.Data;

@Data
public class RegisterVariationDetailResponse {
    private int variationDetailId;
    private int variationId;
    private String description;

    public RegisterVariationDetailResponse(int variationDetailId, int variationId, String description) {
        this.variationDetailId = variationDetailId;
        this.variationId = variationId;
        this.description = description;
    }
}
