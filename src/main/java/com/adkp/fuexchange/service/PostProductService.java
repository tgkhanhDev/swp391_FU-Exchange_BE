package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.pojo.PostProduct;

import java.awt.print.Pageable;
import java.util.List;

public interface PostProductService {
    List<PostProductDTO> viewMorePostProduct(int current, String campusId, String postTypeId, String name);

    PostProductDTO getPostProductById(int postProductId);
    long countPostProduct(String campusId, String postTypeId, String name, List<PostProductDTO> productDTOList);
}
