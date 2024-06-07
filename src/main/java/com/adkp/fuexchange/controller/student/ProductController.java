package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.ProductDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.adkp.fuexchange.request.UpdateInformationProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Tag(name = "Product")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
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

    @PutMapping("/update-information")
    public ResponseObject<Object> UpdateInformation(@RequestBody UpdateInformationProductRequest updateInformationProductRequest
    ) {

        if (updateInformationProductRequest.getProductDetailId().getProductName() != null
                && updateInformationProductRequest.getPrice() >= 0
        ) {
            return productService.updateProductInformation(updateInformationProductRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin cập nhật sản phẩm ")
                .build();
    }

    @GetMapping("/get-by-variation")
    @Operation(summary = "Get product by variation detail")
    public ResponseObject<Object> getProductVariationId(@RequestParam List<Integer> variationDetailId) {

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(productService.getProductByVariationDetailId(variationDetailId))
                .content("Lấy sản phẩm thành công")
                .build();
    }
}
