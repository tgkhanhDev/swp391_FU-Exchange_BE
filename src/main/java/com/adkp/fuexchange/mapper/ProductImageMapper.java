package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.ProductImageDTO;
import com.adkp.fuexchange.model.ProductImage;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {

    @Mapping(source = "productImageId", target = "productImageId")
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "productDetailId", target = "productDetail", ignore = true)
    ProductImageDTO toProductImageDTO(ProductImage productImage);

    @InheritInverseConfiguration(name = "toProductImageDTO")
    ProductImage toProductImage(ProductImageDTO productImageDto);

    List<ProductImageDTO> toProductImageDTOList(List<ProductImage> productImageList);

    List<ProductImage> toProductImageList(List<ProductImageDTO> productImageDTOList);
}
