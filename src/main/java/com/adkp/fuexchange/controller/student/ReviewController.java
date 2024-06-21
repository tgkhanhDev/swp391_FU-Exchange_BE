package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.ReviewResponse;
import com.adkp.fuexchange.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

        List<ReviewDTO> reviewDTOS = reviewService.getReviewByOrderId(orderId);

        int status = HttpStatus.OK.value();
        String message = HttpStatus.OK.name();
        String content = "Xem đánh giá thành công!";

        if (reviewDTOS == null) {
            status = HttpStatus.BAD_REQUEST.value();
            message = HttpStatus.BAD_REQUEST.name();
            content = "Đơn hàng này chưa đánh giá!";
        }

        return ResponseObject.builder()
                .status(status)
                .message(message)
                .content(content)
                .data(reviewDTOS)
                .build();
    }

    @Operation(summary = "View all review by postPostProductId")
    @GetMapping("/all/{post-product}")
    public ResponseObject<Object> getReviewByPostProduct(@PathVariable("post-product") Integer postProductId) {

        ReviewResponse reviewResponse = reviewService.getReviewByPostProduct(postProductId);

        int status = HttpStatus.OK.value();
        String message = HttpStatus.OK.name();
        String content = "Xem đánh giá thành công!";

        if (reviewResponse == null) {
            status = HttpStatus.BAD_REQUEST.value();
            message = HttpStatus.BAD_REQUEST.name();
            content = "Bài đăng này chưa có đánh giá!";
        }

        return ResponseObject.builder()
                .status(status)
                .message(message)
                .content(content)
                .data(reviewResponse)
                .build();
    }
}
