package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.ReviewMapper;
import com.adkp.fuexchange.repository.ReviewRepository;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper, ReviewRepository reviewRepository) {
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ResponseObject<Object> getReviewByOrderId(Integer orderId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        reviewMapper.toReviewDTOList(reviewRepository.getReviewByOrderId(orderId))
                )
                .build();
    }
}
