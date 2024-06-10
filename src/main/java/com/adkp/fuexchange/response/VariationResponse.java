package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.VariationDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariationResponse {

    private int variationId;

    private String variationName;

    private VariationDetail variationDetail;
}
