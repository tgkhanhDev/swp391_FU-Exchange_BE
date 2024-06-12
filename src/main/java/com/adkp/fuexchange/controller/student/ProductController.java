package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.RegisterProductRequest;
import com.adkp.fuexchange.request.UpdateProductStatusRequest;
import com.adkp.fuexchange.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@Tag(name = "Product")
@Validated
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Filter product")
    @GetMapping("/{current}")
    public ResponseObject<Object> viewMoreProduct(
            @PathVariable("current") int current,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sellerID", required = true) int sellerID
    ) {
        return productService.topProductByUserIdAndName(sellerID, name, current);

    }

    @Operation(summary = "Get detail product by productId")
    @GetMapping("detail/{productId}")
    public ResponseObject<Object> viewDetailProductByProductId(
            @PathVariable("productId") int productID
    ) {
        return productService.getProductByProductID(productID);
    }

    @Operation(summary = "Create a product")
    @PostMapping("/create-product")
    public ResponseObject<Object> createAProduct(
            @Valid @RequestBody RegisterProductRequest registerProductRequest
            ) {
        return productService.createProduct(registerProductRequest);
    }



    @Operation(summary = "Update a product")
    @PutMapping(value = "/update-information", consumes = "application/json;charset=UTF-8")
    public ResponseObject<Object> UpdateInformation(@Valid @RequestBody UpdateInformationProductRequest updateInformationProductRequest) {

        if (updateInformationProductRequest.getProductID() != null

        ) {
           return productService.updateProductInformation(updateInformationProductRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin cập nhật sản phẩm ")
                .build();
    }

    @Operation(summary = "Delete a product")
    @PutMapping(value = "/update-status")
    public ResponseObject<Object> UpdateStatus(@RequestBody UpdateProductStatusRequest updateProductStatusRequest ){
       if(updateProductStatusRequest!=null){

           return productService.updateStatus(updateProductStatusRequest);
       }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Không tìm thấy thông tin sản phẩm ")
                .build();
    }
}
