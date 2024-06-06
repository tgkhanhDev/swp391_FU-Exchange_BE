package com.adkp.fuexchange.dto;

import lombok.Data;

import java.util.List;

@Data
public class VariationDTO {

    private int variationId;

    private String variationName;

    private List<VariationDetailDTO> variationDetail;

}
