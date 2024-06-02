package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.mapper.ReviewMapper;
import com.adkp.fuexchange.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewMapper reviewMapper;

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper, ReviewRepository reviewRepository) {
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDTO> getReviewByOrderId(Integer orderId) {
        return reviewMapper.toReviewDTOList(reviewRepository.getReviewByOrderId(orderId));
    }
}
