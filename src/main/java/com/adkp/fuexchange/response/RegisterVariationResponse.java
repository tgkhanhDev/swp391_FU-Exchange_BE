package com.adkp.fuexchange.response;

import lombok.Data;

@Data
public class RegisterVariationResponse {
    private int variationId;
    private String variationName;

    public RegisterVariationResponse(int variationId, String variationName) {
        this.variationId = variationId;
        this.variationName = variationName;
    }
}
