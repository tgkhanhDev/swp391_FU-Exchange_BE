package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.CategoryDTO;
import com.adkp.fuexchange.dto.ProductDetailDTO;
import com.adkp.fuexchange.model.Category;
import com.adkp.fuexchange.model.ProductDetail;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(source = "categoryId", target = "categoryId")
    @Mapping(source = "categoryName", target = "categoryName")
    CategoryDTO toCategoryDTO(Category category);

    @InheritInverseConfiguration(name = "toCategoryDTO")
    Category toCategory(CategoryDTO categoryDTO);
}
