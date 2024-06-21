package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.OrderService;
import com.adkp.fuexchange.service.RegisteredStudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@Tag(name = "Impact on registered student")
@Validated
public class RegisteredStudentController {

    private final RegisteredStudentService registeredStudentService;

    private final OrderService orderService;

    @Autowired
    public RegisteredStudentController(RegisteredStudentService registeredStudentService, OrderService orderService) {
        this.registeredStudentService = registeredStudentService;
        this.orderService = orderService;
    }

    @Operation(summary = "View profile of registered student")
    @GetMapping("/{registeredStudentId}")
    public ResponseObject<Object> viewProfile(@PathVariable("registeredStudentId") Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        registeredStudentService.viewProfile(registeredStudentId)
                )
                .build();
    }

    @Operation(summary = "view order detail for student by registeredStudentId")
    @GetMapping("/order-detail/{registeredStudentId}/{orderId}")
    public ResponseObject<Object> getOrderDetailBySellerIdAndOrderId(
            @PathVariable("registeredStudentId") Integer registeredStudentId,
            @PathVariable("orderId") Integer orderId,
            @RequestParam("orderStatusId") Integer orderStatusId
    ) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(registeredStudentService.getOrdersDetailByRegisteredStudentId(registeredStudentId, orderId, orderStatusId))
                .content("Xem thành công!")
                .build();
    }

    @Operation(summary = "Get information by registeredStudentId")
    @GetMapping("/order/{registeredStudentId}")
    public ResponseObject<Object> getOrderByRegisterId(@PathVariable("registeredStudentId") Integer registeredStudentId) {

        List<OrdersDTO> ordersDTOList = orderService.getOrderByRegisterId(registeredStudentId);
        if (ordersDTOList == null) {
            return ResponseObject.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.name())
                    .content("Bạn Chưa có đơn hàng nào!")
                    .build();
        }
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thành công!")
                .data(ordersDTOList)
                .build();
    }

    @Operation(summary = "Update of registered student")
    @PutMapping("/update-password")
    public ResponseObject<Object> updatePassword(@RequestBody @Valid UpdatePasswordRequest updatePasswordRequest) {

        return registeredStudentService.updatePassword(updatePasswordRequest);
    }
}
