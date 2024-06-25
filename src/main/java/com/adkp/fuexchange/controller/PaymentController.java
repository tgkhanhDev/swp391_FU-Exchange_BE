package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.dto.OrderPostProductDTO;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PaymentService;
import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayResponse;
import com.adkp.fuexchange.service.thirdparty.vnpay.VnPayService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/payment")
@Tag(name = "Order")
@Validated
public class PaymentController {

    private final VnPayService vnPayService;

    private final PaymentService paymentService;

    private final RegisteredStudentRepository registeredStudentRepository;


    @Autowired
    public PaymentController(VnPayService vnPayService, PaymentService paymentService, RegisteredStudentRepository registeredStudentRepository) {
        this.vnPayService = vnPayService;
        this.paymentService = paymentService;
        this.registeredStudentRepository = registeredStudentRepository;
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
    @Operation(summary = "Payment of VnPay")
    @PostMapping("/vn-pay")
    public VnPayResponse payment(
            @Valid @RequestBody OrdersRequest ordersRequest,
            @RequestHeader HttpHeaders headers
    ) {

        if (registeredStudentRepository.getReferenceById(ordersRequest.getRegisteredStudentId()).getDeliveryAddress() == null) {
            return VnPayResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Chưa có địa chỉ nhận hàng. Vui lòng điền đầy đủ thông tin trước khi mua hàng!")
                    .build();
        }
        vnPayService.validateQuantity(ordersRequest.getPostProductToBuyRequests());
        return vnPayService.vnPayPayment(ordersRequest, headers);
    }

    @GetMapping("/vn-pay/call-back")
    @Hidden
    public ResponseObject<Object> paymentCallBack(@RequestParam("vnp_ResponseCode") String vnp_ResponseCode) {

        int status = HttpStatus.BAD_REQUEST.value();

        String message = HttpStatus.BAD_REQUEST.name();

        String content = "Mua hàng thất bại!";

        if (vnPayService.vnPayPaymentCallBack(vnp_ResponseCode)) {
            status = HttpStatus.OK.value();

            message = HttpStatus.BAD_REQUEST.name();

            content = "Mua hàng thành công!";
        }

        return ResponseObject.builder()
                .status(status)
                .message(message)
                .content(content)
                .build();
    }

    @ApiResponses(value = {
            @ApiResponse(description = "Using for payment with cod",
                    content = @Content
            )
    })
    @Operation(summary = "Pay order for all")
    @PostMapping(value = "/pay-order", consumes = "application/json")
    public ResponseObject<Object> payOrders(@Valid @RequestBody OrdersRequest ordersRequest) {

        int status = HttpStatus.OK.value();
        String message = HttpStatus.OK.name();
        String content = "Mua hàng thành công!";

        if (registeredStudentRepository.getReferenceById(ordersRequest.getRegisteredStudentId()).getDeliveryAddress() == null) {
            return ResponseObject.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Chưa có địa chỉ nhận hàng. Vui lòng điền đầy đủ thông tin trước khi mua hàng!")
                    .build();
        }

        List<OrderPostProductDTO> ordersResult = paymentService.payOrders(ordersRequest);

        if (ordersResult == null) {
            status = HttpStatus.OK.value();
            message = HttpStatus.OK.name();
            content = "Mua hàng thất bại!";
        }

        return ResponseObject.builder()
                .status(status)
                .message(message)
                .content(content)
                .data(ordersResult)
                .build();
    }

}
