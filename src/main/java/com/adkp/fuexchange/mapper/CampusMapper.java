package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.model.Campus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CampusMapper {

    @Mapping(source = "campusId", target = "campusId")
    @Mapping(source = "campusName", target = "campusName")
    CampusDTO toCampusDTO(Campus campus);

    @InheritInverseConfiguration(name = "toCampusDTO")
    Campus toCampus(CampusDTO campusDTO);
}