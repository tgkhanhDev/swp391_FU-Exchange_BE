package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> viewAllCartItemByStudentId(int studentId);
}
