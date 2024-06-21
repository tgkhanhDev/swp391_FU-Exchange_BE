package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ReviewDTO;
import com.adkp.fuexchange.response.ReviewResponse;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviewByOrderId(Integer orderId);

    ReviewResponse getReviewByPostProduct(Integer postProductId);
}
