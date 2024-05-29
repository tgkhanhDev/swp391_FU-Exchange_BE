package com.adkp.fuexchange.controller.student;

import org.springframework.web.bind.annotation.*;

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
