package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.pojo.CartPost;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartPostMapper {
    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "postProductId", target = "postProductId")
    CartPostDTO toCartPostDTO(CartPost cartPost);

    @InheritInverseConfiguration(name = "toCartPostDTO")
    CartPost toCartPost(CartPostDTO cartPostDTO);

    List<CartPostDTO> toCartPostDTOList(List<CartPost> cartPost);

    List<CartPost> toCartPostList(List<CartPostDTO> cartPost);

}
