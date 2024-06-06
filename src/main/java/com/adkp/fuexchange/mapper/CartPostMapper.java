package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.pojo.CartPost;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CartMapper.class, PostProductMapper.class, VariationDetailMapper.class}
)
public interface CartPostMapper {
    @Mapping(source = "cartPostId", target = "cartPostId")
    @Mapping(source = "cartId", target = "cart")
    @Mapping(source = "postProductId", target = "postProduct")
    @Mapping(source = "postProductId.productId.sellerId", target = "postProduct.product.seller", ignore = true)
    @Mapping(source = "variationDetailId", target = "variationDetail")
    @Mapping(source = "quantity", target = "quantity")
    CartPostDTO toCartPostDTO(CartPost cartPost);

    @InheritInverseConfiguration(name = "toCartPostDTO")
    CartPost toCartPost(CartPostDTO cartPostDTO);

    List<CartPostDTO> toCartPostDTOList(List<CartPost> cartPost);

    List<CartPost> toCartPostList(List<CartPostDTO> cartPost);

}
