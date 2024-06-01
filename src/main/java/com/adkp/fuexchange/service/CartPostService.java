package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.CartPost;

import java.util.List;

public interface CartPostService {
    List<CartPost> viewCartPostItemByStudentId(String studentId);
}
