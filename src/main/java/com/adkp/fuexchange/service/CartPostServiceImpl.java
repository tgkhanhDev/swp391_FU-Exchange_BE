package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.mapper.CartPostMapper;
import com.adkp.fuexchange.repository.CartPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartPostServiceImpl implements CartPostService{
    private final CartPostRepository cartPostRepository;
    private final CartPostMapper cartPostMapper;

    @Autowired
    public CartPostServiceImpl(CartPostRepository cartPostRepository, CartPostMapper cartPostMapper) {
        this.cartPostRepository = cartPostRepository;
        this.cartPostMapper = cartPostMapper;
    }

    @Override
    public List<CartPostDTO> viewCartPostItemByStudentId(String studentId) {
        return cartPostMapper.toCartPostDTOList(cartPostRepository.getCartPostByStudentId(studentId));
    }

}
