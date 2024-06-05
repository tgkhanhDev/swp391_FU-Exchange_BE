package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.RegisterToSellerRequest;
import com.adkp.fuexchange.request.UpdateInformationSellerRequest;
import com.adkp.fuexchange.request.UpdateStatusRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.SellerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
@Tag(name = "Seller")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/{sellerId}")
    public ResponseObject<Object> viewInformationSellerById(
            @PathVariable int sellerId
    ) {
        return sellerService.viewInformationSellerById(sellerId);
    }

    @PostMapping("/register-to-seller")
    public ResponseObject<Object> registerToSeller(@RequestBody RegisterToSellerRequest registerToSellerRequest) {
        if (
                registerToSellerRequest.getRegisteredStudentId() != null
                        && registerToSellerRequest.getBankingNumber() != null
                        && registerToSellerRequest.getBankingName() != null
                        && registerToSellerRequest.getPassword() != null
        ) {
            return sellerService.registerToSeller(registerToSellerRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Vui lòng nhập đầy đủ thông tin")
                .build();
    }

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

}
