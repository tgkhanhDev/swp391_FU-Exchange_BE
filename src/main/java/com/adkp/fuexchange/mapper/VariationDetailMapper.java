package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.VariationDetailDTO;
import com.adkp.fuexchange.pojo.VariationDetail;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VariationDetailMapper {

    @Mapping(source = "variationDetailId", target = "variationDetailId")
    @Mapping(source = "variationId", target = "variationId")
    @Mapping(source = "description", target = "description")
    VariationDetailDTO toVariationDetailDTO(VariationDetail variationDetail);

    @InheritInverseConfiguration(name = "toVariationDetailDTO")
    VariationDetail toVariationDetail(VariationDetailDTO variationDetailDTO);

    List<VariationDetailDTO> toVariationDetailTOList(List<VariationDetail> variationDetailList);

    List<VariationDetail> toVariationDetailList(List<VariationDetailDTO> variationDetailDTOList);
}
