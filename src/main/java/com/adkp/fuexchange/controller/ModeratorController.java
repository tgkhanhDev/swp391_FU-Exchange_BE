package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.request.UpdatePostStatus;
import com.adkp.fuexchange.response.MetaResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ModeratorService;
import com.adkp.fuexchange.service.PostProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
@Tag(name = "Moderator")
@Validated
public class ModeratorController {

    private final ModeratorService moderatorService;

    private final PostProductService postProductService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService, PostProductService postProductService) {
        this.moderatorService = moderatorService;
        this.postProductService = postProductService;
    }

    @GetMapping("/view-register-to-seller-request")
    public ResponseObject<Object> getRegisterToSellerRequest() {
        List<SellerDTO> sellerDTO = moderatorService.viewRegisterToSellerRequest();
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(sellerDTO)
                .build();
    }

    @GetMapping("/view-create-order-request")
    public ResponseObject<Object> viewCreateOrderRequest() {

        List<OrdersDTO> ordersDTO = moderatorService.viewCreateOrderRequest();

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(ordersDTO)
                .build();
    }

    @GetMapping("/view-create-post-product")
    public ResponseObject<Object> viewCreatePostProduct() {

        List<PostProductDTO> postProductDTO = moderatorService.viewCreatePostProductRequest();

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(postProductDTO)
                .build();
    }

    @Operation(summary = "Update status of post product")
    @PutMapping("/update/status-post-product")
    public ResponseObject<Object> updateStatusPostProduct(@RequestBody @Valid UpdatePostStatus updatePostStatus) {

        PostProductDTO postProductDTO = postProductService.updateStatusPostProduct(updatePostStatus);

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật thành công")
                .data(postProductDTO)
                .build();
    }


    @GetMapping("/filter-post-product")
    public ResponseObject<Object> filterPostProductForStaff(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "sellerName", required = false) String sellerName,
            @RequestParam(value = "postTypeId", required = false) Integer postTypeId,
            @RequestParam(value = "campusId", required = false) Integer campusId,
            @RequestParam(value = "postStatus", required = false) Integer postStatus
    ) {

        List<PostProductDTO> postProductDTOs = postProductService.filterPostProductForStaff(
                page - 1,
                sellerName,
                postTypeId,
                campusId,
                postStatus
        );

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thêm thành công!")
                .data(postProductDTOs)
                .meta(new MetaResponse(postProductService.countAllPostProduct(), 6))
                .build();
    }
}
