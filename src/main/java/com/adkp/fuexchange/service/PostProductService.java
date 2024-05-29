package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;

import java.util.List;

public interface PostProductService {
    List<PostProductDTO> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name, Integer categoryId);

    PostProductDTO getPostProductById(int postProductId);
    long countPostProduct(Integer campusId, Integer postTypeId, String name, Integer categoryId, List<PostProductDTO> productDTOList);
}
