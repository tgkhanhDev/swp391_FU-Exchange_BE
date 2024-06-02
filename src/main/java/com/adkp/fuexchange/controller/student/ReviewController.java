package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.service.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Tag(name = "Review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{orderId}")
    public List<ReviewDTO> getReviewByOrder(@PathVariable("orderId") Integer orderId){
       return reviewService.getReviewByOrderId(orderId);
    }
}
