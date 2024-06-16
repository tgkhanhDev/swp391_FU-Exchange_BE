package com.adkp.fuexchange.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VariationDTO {

    private int variationId;

    private String variationName;

    private List<VariationDetailDTO> variationDetail;

}
