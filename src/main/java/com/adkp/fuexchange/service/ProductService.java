package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> viewMoreProduct(int current);

    long countTotalPostProduct();

}
