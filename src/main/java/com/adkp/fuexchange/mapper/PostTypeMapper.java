package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.PostTypeDTO;
import com.adkp.fuexchange.model.PostType;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostTypeMapper {

    @Mapping(source = "postTypeId", target = "postTypeId")
    @Mapping(source = "postTypeName", target = "postTypeName")
    PostTypeDTO toPostTypeDTO(PostType postType);

    @InheritInverseConfiguration(name = "toPostTypeDTO")
    PostType toPostType(PostTypeDTO postTypeDTO);
}
