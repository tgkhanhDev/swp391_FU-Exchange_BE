package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.UpdatePasswordRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface RegisteredStudentService {

    ResponseObject<Object> viewProfile(Integer registeredStudentId);

    ResponseObject<Object> updatePassword(UpdatePasswordRequest updatePasswordRequest);
}
