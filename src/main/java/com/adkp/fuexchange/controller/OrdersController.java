package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayResponse;
import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Tag(name = "Order")
public class OrdersController {

    private final VnPayService vnPayService;

    @Autowired
    public OrdersController(VnPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    @ApiResponses(value = {
            @ApiResponse(description = """
                    Thành công: 9704198526191432198 |\s
                    Thẻ không đủ số dư: 9704195798459170488 |\s
                    Thẻ chưa kích hoạt: 9704192181368742 |\s
                    Thẻ bị khóa: 9704193370791314 |\s
                    Thẻ bị hết hạn: 9704194841945513 |\s
                    Name: NGUYEN VAN A |\s
                    Date: 07/15 |\s
                    Link dashboard: https://sandbox.vnpayment.vn/merchantv2/Home/Dashboard.htm |\s
                    gmail: nguyenhoangan060703@gmail.com |\s
                    password: Kaka1342""", content = @Content)
    })
    @GetMapping("/vn-pay")
    public VnPayResponse payment(
            @RequestParam String amountRequest,
            HttpServletRequest request
    ){
       return vnPayService.vnPayPayment(amountRequest, request);
    }

    @GetMapping("/vn-pay/call-back")
    public VnPayResponse paymentCallBack(HttpServletRequest request){
        return vnPayService.vnPayPaymentCallBack(request);
    }
}
