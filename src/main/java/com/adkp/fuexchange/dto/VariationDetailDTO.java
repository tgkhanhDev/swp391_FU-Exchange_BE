package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Variation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class VariationDetailDTO {

    private int variationDetailId;

    @JsonIgnore
    private Variation variationId;

    private String description;
}
