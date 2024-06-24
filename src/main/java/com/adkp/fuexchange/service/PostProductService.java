package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.request.CreatePostProductRequest;
import com.adkp.fuexchange.request.UpdatePostProductRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface PostProductService {
    ResponseObject<Object> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name, Integer categoryId);

    ResponseObject<Object> getPostProductById(int postProductId);

    ResponseObject<Object>getPostProductBySellerId(int sellerID);

    ResponseObject<Object>getPostProductByRegisteredStudentId(int registeredStudentId);

    PostProductDTO updatePostProduct(UpdatePostProductRequest updatePostProductRequest);

    PostProductDTO createPostProduct(CreatePostProductRequest createPostProductRequest);
}
