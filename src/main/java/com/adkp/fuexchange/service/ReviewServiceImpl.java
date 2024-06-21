package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.mapper.ReviewMapper;
import com.adkp.fuexchange.pojo.Review;
import com.adkp.fuexchange.repository.ReviewRepository;
import com.adkp.fuexchange.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ReviewDTO> getReviewByOrderId(Integer orderId) {
        List<Review> reviewDTO = reviewRepository.getReviewByOrderId(orderId);

        return  reviewMapper.toReviewDTOList(reviewDTO);
    }

    @Override
    public ReviewResponse getReviewByPostProduct(Integer postProductId) {

        List<Review> review = reviewRepository.getReviewByPostProduct(postProductId);

        Integer totalReview = reviewRepository.countReviewByPostProductId(postProductId);

        Double totalRating = reviewRepository.calcAvgRatingByPostProductId(postProductId);

        if (totalReview != null && totalRating != null) {
            return ReviewResponse.builder()
                    .reviews(reviewMapper.toReviewDTOList(review))
                    .totalReview(totalReview)
                    .totalRating(totalRating)
                    .build();
        }

        return null;
    }
}
