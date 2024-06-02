package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> getReviewByOrderId(Integer orderId);
}
