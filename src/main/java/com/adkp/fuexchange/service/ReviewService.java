package com.adkp.fuexchange.service;

import com.adkp.fuexchange.response.ResponseObject;

public interface ReviewService {

    ResponseObject<Object> getReviewByOrderId(Integer orderId);
}
