package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import com.adkp.fuexchange.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Tag(name="product")
public class ProductController {
  private  final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping("/{current}")
    public ResponseObject<Object>viewMoreProduct(
            @PathVariable("current") int current,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "sellerID",required = true) int sellerID

    ){
       return  productService.topProductByUserIdAndName(sellerID,name,current);

    }

    @GetMapping("detail/{productId}")
    public ResponseObject<Object>viewMoreProduct(
            @PathVariable("productId") int productID

    ){
        return  productService.getProductByProductID(productID);

    }

    @PutMapping("/update-information")
    public ResponseObject<Object>UpdateInformation(@RequestBody UpdateInformationProductRequest updateInformationProductRequest
    ){

        if(updateInformationProductRequest.getProductDetailId().getProductName()!=null
                && updateInformationProductRequest.getPrice()>=0
        ){
            return productService.updateProductInformation(updateInformationProductRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin cập nhật sản phẩm ")
                .build();
    }



//    private final ProductService prodcuctService;
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
