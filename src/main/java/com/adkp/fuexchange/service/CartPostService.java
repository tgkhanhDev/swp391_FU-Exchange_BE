package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartDTO;
import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.ResponseObject;

import java.util.List;

public interface CartPostService {
    List<PostProductDTO> viewCartPostProductByStudentId(String studentId);

    ResponseObject addToCart(CartRequest cartRequest);
}
