package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface CartPostService {
    ResponseObject<Object> viewCartPostProductByStudentId(String studentId);
    ResponseObject<Object> addToCart(CartRequest cartRequest);
}
