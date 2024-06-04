package com.adkp.fuexchange.controller;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{registeredStudentId}")
    public ResponseObject<Object> getOrderByRegisterId(@PathVariable("registeredStudentId") Integer registeredStudentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .data(orderService.getOrderByRegisterId(registeredStudentId))
                .build();
    }

}
