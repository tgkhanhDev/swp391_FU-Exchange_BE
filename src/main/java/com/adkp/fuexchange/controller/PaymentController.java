package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PaymentService;
import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayResponse;
import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@Tag(name = "Order")
public class PaymentController {

    private final VnPayService vnPayService;

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(VnPayService vnPayService, PaymentService paymentService) {
        this.vnPayService = vnPayService;
        this.paymentService = paymentService;
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
            @RequestParam("amount") String amountRequest,
            HttpServletRequest request
    ) {
        return vnPayService.vnPayPayment(amountRequest, request);
    }

    @GetMapping("/vn-pay/call-back")
    public ResponseObject<Object> vnPayCallBack(HttpServletRequest request) {
        return vnPayService.vnPayCallBack(request);
    }

    @PostMapping("/cod")
    public ResponseObject<Object> paymentCod(@RequestBody OrdersRequest ordersRequest) {
        if (
                ordersRequest.getRegisteredStudentId() != 0 ||
                        ordersRequest.getPaymentMethodId() != 0 ||
                        ordersRequest.getPostProductToBuyRequests().stream().anyMatch(
                                postRequest -> postRequest.getQuantity() != 0 ||
                                        postRequest.getVariationDetailId() == 0 ||
                                        postRequest.getPostProductId() == 0 ||
                                        postRequest.getPrice() == 0
                        )
        ) {
            return paymentService.paymentCod(ordersRequest);
        }
        return ResponseObject.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(HttpStatus.BAD_REQUEST.name())
                .content("Không đủ thông tin mua hàng!")
                .build();
    }
}