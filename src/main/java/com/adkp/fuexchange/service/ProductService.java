package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface ProductService {

    ResponseObject<Object> viewMoreProduct(int current);

    long countTotalPostProduct();

}
