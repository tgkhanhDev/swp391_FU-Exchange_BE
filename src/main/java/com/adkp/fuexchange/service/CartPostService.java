package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.request.CartRequest;

import java.util.List;

public interface CartPostService {
    List<CartPostDTO> viewCartPostProductByStudentId(Integer registeredStudentId);

    List<CartPost> addToCart(CartRequest cartRequest);

    boolean removeFromCart(CartRequest cartRequest);

}
