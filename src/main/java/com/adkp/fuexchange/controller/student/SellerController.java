package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@Tag(name = "Seller")
@Validated
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Operation(summary = "View profile of seller by sellerId")
    @GetMapping("/{sellerId}")
    public ResponseObject<Object> viewInformationSellerById(
            @PathVariable int sellerId
    ) {
        return sellerService.viewInformationSellerById(sellerId);
    }

    @Operation(summary = "Register to seller")
    @PostMapping("/register-to-seller")
    public ResponseObject<Object> registerToSeller(
            @Valid @RequestBody RegisterToSellerRequest registerToSellerRequest
    ) {
        return sellerService.registerToSeller(registerToSellerRequest);
    }

    @Operation(summary = "Update information of seller")
    @PutMapping("/update-information")
    public ResponseObject<Object> updateInformationSeller(@RequestBody UpdateInformationSellerRequest updateInformationSellerRequest) {

        if (
                updateInformationSellerRequest.getBankingName() != null
                        && updateInformationSellerRequest.getBankingNumber() != null
                        && updateInformationSellerRequest.getSellerId() != null
        ) {
            return sellerService.updateInformationSeller(updateInformationSellerRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin")
                .build();
    }

    @Operation(summary = "Update status of seller")
    @PutMapping("/update-status")
    public ResponseObject<Object> updateStatusSeller(@RequestBody UpdateStatusRequest updateStatusRequest) {

        if (
                updateStatusRequest.getIsActive() != null && updateStatusRequest.getSellerId() != null
        ) {
            return sellerService.updateStatusSeller(updateStatusRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin")
                .build();
    }

    @Operation(summary = "delete of seller")
    @DeleteMapping("/{sellerId}")
    public ResponseObject<Object>deleteSellerByID(
            @PathVariable("sellerId") int sellerId
    ){
        sellerService.deleteSellerByID(sellerId);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xóa thành công")
                .build();
    }

}
