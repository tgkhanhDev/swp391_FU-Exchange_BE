package com.adkp.fuexchange.response;

import lombok.Data;

@Data
public class VariationResponse {

    private int variationId;
    private String variationName;

    public VariationResponse(int variationId, String variationName) {
        this.variationId = variationId;
        this.variationName = variationName;
    }
}
