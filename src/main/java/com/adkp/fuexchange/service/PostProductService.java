package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface PostProductService {
    ResponseObject<Object> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name, Integer categoryId);

    ResponseObject<Object> getPostProductById(int postProductId);
}
