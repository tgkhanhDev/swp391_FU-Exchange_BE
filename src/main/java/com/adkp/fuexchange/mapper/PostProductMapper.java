package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.model.PostProduct;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PostStatusMapper.class, PostTypeMapper.class, CampusMapper.class, ProductMapper.class})
public interface PostProductMapper {

    @Mapping(source = "postProductId", target = "postProductId")
    @Mapping(source = "productId", target = "product")
    @Mapping(source = "postTypeId", target = "postType")
    @Mapping(source = "campusId", target = "campus")
    @Mapping(source = "postStatusId", target = "postStatus")
    @Mapping(source = "quantity", target = "quantity")
    @Mapping(source = "createDate", target = "createDate", dateFormat = "dd/MM/yyyy")
    @Mapping(source = "content", target = "content")
    PostProductDTO toPostProductDTO(PostProduct postProduct);

    @InheritInverseConfiguration(name = "toPostProductDTO")
    PostProduct toPostProduct(PostProductDTO postProductDTO);

    List<PostProductDTO> toPostProductDTOList(List<PostProduct> postProductList);

    List<PostProduct> toPostProductList(List<PostProductDTO> productDTOList);
}
