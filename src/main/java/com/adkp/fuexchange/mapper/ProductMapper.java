package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.pojo.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductDetailMapper.class, ProductImageMapper.class, VariationMapper.class})
public interface ProductMapper {


    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "productDetailId", target = "detail")
    @Mapping(source = "productDetailId.productImageId", target = "image")
    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "price", target = "price")
    @Mapping(source = "variationId", target = "variation")
    @Mapping(source = "productDetailId", target = "productDetailId", ignore = true)
    @Mapping(source = "sellerId", target = "sellerId", ignore = true)
    ProductDTO toProductDTO(Product product);

    @InheritInverseConfiguration(name = "toProductDTO")
    Product toProduct(ProductDTO productDTO);

    List<ProductDTO> toProductDTOList(List<Product> productList);

    List<Product> toProductList(List<ProductDTO> productDTOList);

}
