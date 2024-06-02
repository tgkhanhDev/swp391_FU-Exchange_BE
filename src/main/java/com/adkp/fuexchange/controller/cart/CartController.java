package com.adkp.fuexchange.controller.cart;

import com.adkp.fuexchange.dto.CartDTO;
import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.CartPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Get cart by studentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Data sample: DE170001",
                    content = {@Content(mediaType = "application/json")}),
    })
    public List<PostProductDTO> viewCartPostByStudentId(@PathVariable String studentId){
        return cartPostService.viewCartPostProductByStudentId(studentId);
    }

    @PostMapping("/cart")
    public ResponseObject addPostProductToCart(@RequestBody CartRequest cartRequest){
        return cartPostService.addToCart(cartRequest);
    }


}
