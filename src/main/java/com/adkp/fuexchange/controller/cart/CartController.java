package com.adkp.fuexchange.controller.cart;

import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.service.CartPostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="Shopping Cart")
public class CartController {
    private final CartPostService cartPostService;

    @Autowired
    public CartController(CartPostService cartPostService){
        this.cartPostService = cartPostService;
    }

    @GetMapping("/cart/{studentId}")
    public List<CartPost> viewCartPostByStudentId(@PathVariable String studentId){
        return cartPostService.viewCartPostItemByStudentId(studentId);
    }


}
