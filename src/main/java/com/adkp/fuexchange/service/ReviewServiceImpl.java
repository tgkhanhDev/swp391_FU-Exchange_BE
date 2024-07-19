package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.mapper.ReviewMapper;
import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.pojo.Review;
import com.adkp.fuexchange.repository.OrdersRepository;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.repository.ReviewRepository;
import com.adkp.fuexchange.request.RegisterReviewRequest;
import com.adkp.fuexchange.request.UpdateInformationReviewRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.ReviewResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final PostProductRepository postProductRepository;
    private final OrdersRepository ordersRepository;

    @Autowired
    public ReviewServiceImpl(ReviewMapper reviewMapper, ReviewRepository reviewRepository, PostProductRepository postProductRepository, OrdersRepository ordersRepository) {
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
        this.postProductRepository = postProductRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public List<ReviewDTO> getReviewByOrderId(Integer orderId) {
        List<Review> reviewDTO = reviewRepository.getReviewByOrderId(orderId);

        return reviewMapper.toReviewDTOList(reviewDTO);
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

    @Override
    @Transactional
    public ReviewDTO createReview(RegisterReviewRequest registerReviewRequest) {

        Orders orders = ordersRepository.getReferenceById(registerReviewRequest.getOrderId());
        if (orders.getOrderStatusId().getOrderStatusId() != 5) {
            return null;
        }

        Review review = reviewRepository.getReviewByOrderAndPost(
                registerReviewRequest.getPostProductId(), registerReviewRequest.getOrderId()
        );

        if (review == null) {
            Review newReview = Review.builder()
                    .postProductId(postProductRepository.getReferenceById(registerReviewRequest.getPostProductId()))
                    .orderId(ordersRepository.getReferenceById(registerReviewRequest.getOrderId()))
                    .ratingNumber(registerReviewRequest.getRatingNumber())
                    .description(registerReviewRequest.getDescription())
                    .createTime(LocalDateTime.now())
                    .build();
            return reviewMapper.toReviewDTO(reviewRepository.save(newReview));
        }
        review.setRatingNumber(registerReviewRequest.getRatingNumber());
        review.setDescription(registerReviewRequest.getDescription());
        review.setCreateTime(LocalDateTime.now());

        return reviewMapper.toReviewDTO(reviewRepository.save(review));
    }

    @Override
    @Transactional
    public ResponseObject<Object> updateReview(int reviewID, UpdateInformationReviewRequest updateInformationReviewRequest) {
        Review review = reviewRepository.getReferenceById(reviewID);
        review.setRatingNumber(updateInformationReviewRequest.getRatingNumber());
        review.setDescription(updateInformationReviewRequest.getDescription());
        review.setCreateTime(LocalDateTime.now());
        reviewRepository.save(review);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("cập nhật thành công!")
                .build();
    }

    @Override
    @Transactional
    public void deleteReview(int reviewID) {
        reviewRepository.deleteById(reviewID);
    }
}
