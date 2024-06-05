package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.request.OrderUpdateRequest;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Tag(name = "Order")
public class OrdersController {

    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{registeredStudentId}")
    public ResponseObject<Object> getOrderByRegisterId(@PathVariable("registeredStudentId") Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thành công!")
                .data(orderService.getOrderByRegisterId(registeredStudentId))
                .build();
    }

    @PutMapping("/update")
    public ResponseObject<Object> updateOrder(@RequestBody OrderUpdateRequest orderUpdateRequest){
        orderService.updateOrder(orderUpdateRequest);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật thành công!")
                .build();
    }

}
