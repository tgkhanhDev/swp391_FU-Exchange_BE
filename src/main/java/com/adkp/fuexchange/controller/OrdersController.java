package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@Tag(name = "Order")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{registeredStudentId}")
    public ResponseObject getOrderByRegisterId(@PathVariable("registeredStudentId") Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .ordersDTO(orderService.getOrderByRegisterId(registeredStudentId))
                .build();
    }

}
