package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.response.ResponseObject;

import java.util.List;

public interface CartPostService {
    List<CartPostDTO> viewCartPostItemByStudentId(String studentId);

//    ResponseObject addToCart()
}
