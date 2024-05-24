package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.response.MetaPostProduct;
import com.adkp.fuexchange.response.PostProductResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import com.adkp.fuexchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post-product")
public class PostProductController {
    private final PostProductService postProductService;

    @Autowired
    public PostProductController(PostProductService postProductService) {
        this.postProductService = postProductService;
    }

    @GetMapping("/{current}")
    public PostProductResponse viewMorePostProduct(@PathVariable("current") String current){
        List<PostProductDTO> productDTO = postProductService.viewMorePostProduct(Integer.parseInt(current));
        return PostProductResponse
                .builder()
                .responseObject(new ResponseObject(HttpStatusCode.valueOf(200).value(), "Success"))
                .meta(new MetaPostProduct(postProductService.countTotalPostProduct(), Integer.parseInt(current)))
                .data(productDTO)
                .build();
    }

    @GetMapping("/filter/{campusId}")
    public PostProductResponse postProductByCampus(@PathVariable("campusId") String current){
        List<PostProductDTO> productDTO = postProductService.viewPostProductByCampus(Integer.parseInt(current));
        return PostProductResponse
                .builder()
                .responseObject(new ResponseObject(HttpStatusCode.valueOf(200).value(), "Success"))
                .meta(new MetaPostProduct(postProductService.countTotalPostProduct(), Integer.parseInt(current)))
                .data(productDTO)
                .build();
    }
}
