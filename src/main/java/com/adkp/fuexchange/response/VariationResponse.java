package com.adkp.fuexchange.response;

import com.adkp.fuexchange.dto.VariationDetailDTO;
import com.adkp.fuexchange.pojo.Product;
import com.adkp.fuexchange.pojo.VariationDetail;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VariationResponse {

    private int variationId;

    private String variationName;

    private VariationDetail variationDetail;
}
