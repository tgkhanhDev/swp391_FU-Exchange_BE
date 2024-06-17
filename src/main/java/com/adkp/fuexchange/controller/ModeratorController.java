package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ModeratorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moderator")
@Tag(name = "Moderator")
@Validated
public class ModeratorController {

    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/view-register-to-seller-request")
    public ResponseObject<Object> getRegisterToSellerRequest(){
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(moderatorService.viewRegisterToSellerRequest())
                .build();
    }
}
