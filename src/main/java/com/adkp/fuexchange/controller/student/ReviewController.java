package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
@Tag(name = "Review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    @Operation(summary = "View review of order by OrderId")
    @GetMapping("/{orderId}")
    public ResponseObject<Object> getReviewByOrder(@PathVariable("orderId") Integer orderId) {
        return reviewService.getReviewByOrderId(orderId);
    }
}
