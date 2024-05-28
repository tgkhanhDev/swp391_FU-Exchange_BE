package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;

import java.awt.print.Pageable;
import java.util.List;

public interface PostProductService {
    List<PostProductDTO> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name);

    PostProductDTO getPostProductById(int postProductId);
}
