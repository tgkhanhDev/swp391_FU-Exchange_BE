package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.response.MetaPostProduct;
import com.adkp.fuexchange.response.PostProductResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

//    private final ProductService productService;
//
//    @Autowired
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @GetMapping("/{current}")
//    public PostProductResponse postProductResponse(@PathVariable("current") String current){
//        List<ProductDTO> productDTO = productService.viewMoreProduct(Integer.parseInt(current));
//        return PostProductResponse
//                .builder()
//                .responseObject(new ResponseObject(HttpStatusCode.valueOf(200).value(), "Success"))
//                .meta(new MetaPostProduct(productService.countTotalPostProduct(), Integer.parseInt(current)))
//                .data(productDTO)
//                .build();
//    }
}
