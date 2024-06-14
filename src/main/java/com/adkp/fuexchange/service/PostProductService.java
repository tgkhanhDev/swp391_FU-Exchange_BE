package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.request.UpdatePostProductRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface PostProductService {
    ResponseObject<Object> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name, Integer categoryId);

    ResponseObject<Object> getPostProductById(int postProductId);

    PostProductDTO updatePostProduct(UpdatePostProductRequest updatePostProductRequest);
}
