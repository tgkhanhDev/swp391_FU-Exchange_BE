package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface CartPostService {
    ResponseObject<Object> viewCartPostProductByStudentId(String studentId);

    ResponseObject<Object> viewCartPostById(CartPostEmbeddable cartPostId);

    ResponseObject<Object> addToCart(CartRequest cartRequest);
    ResponseObject<Object> updateCart(CartRequest cartRequest);
    ResponseObject<Object> removeFromCart(CartPostEmbeddable cartPostId);
}
