package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.repository.CartPostMapper;
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
    public List<CartPost> viewCartPostItemByStudentId(String studentId) {
        return cartPostRepository.getCartPostByStudentId(studentId);
    }
}
