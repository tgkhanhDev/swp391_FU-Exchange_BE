package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface CartService {
    ResponseObject<Object> viewAllCartItemByStudentId(int studentId);
}
