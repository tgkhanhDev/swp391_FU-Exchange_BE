package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.RegisteredStudentDTO;
import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.OrderDetailResponse;
import com.adkp.fuexchange.response.ResponseObject;

public interface RegisteredStudentService {

    ResponseObject<Object> viewAllRegisteredStudent(int current,String name);
    RegisteredStudentDTO viewProfile(Integer registeredStudentId);

    ResponseObject<Object> updatePassword(UpdatePasswordRequest updatePasswordRequest);

    OrderDetailResponse getOrdersDetailByRegisteredStudentId(Integer registeredStudentId, Integer orderId, Integer orderStatusId);
    ResponseObject<Object>updateActiveByID(int registeredStudentId, int active);
    ResponseObject<Object>updateRegisterStudentByID(int registeredStudentId, String deliveryAddress);

}
