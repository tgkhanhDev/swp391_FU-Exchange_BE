package com.adkp.fuexchange.repository;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.pojo.CartPost;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartPostMapper {
    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "postProductId", target = "postProductId")
    CartPostDTO toCartPostDTO(CartPost cartPost);

    @InheritInverseConfiguration(name = "toCartPostDTO")
    CartPost toCartPost(CartPostDTO cartPostDTO);

}
