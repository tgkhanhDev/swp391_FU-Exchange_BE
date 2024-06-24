package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.OrderPostProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderPostProduct")
@Tag(name = "orderPostProduct")
public class OrderPostProductController {
   private final OrderPostProductService orderPostProductService;


    public OrderPostProductController(OrderPostProductService orderPostProductService) {
        this.orderPostProductService = orderPostProductService;
    }

   @GetMapping("/{sellerID}")
    public ResponseObject<Object>getSellerSellingPostProduct(
            @PathVariable("sellerID") int sellerID
   ){
            return   orderPostProductService.viewTotalPriceEachPostProductBySellerID(sellerID);
   }

}
