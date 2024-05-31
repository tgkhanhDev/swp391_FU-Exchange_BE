package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.response.ResponseObject;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

    ResponseObject register(RegisterRequest registerRequest);

    ResponseObject checkInformationRegister(String studentId,String identity);

    LoginResponse loginResponse(LoginRequest loginRequest);

    LoginResponse isRegistered(String studentId);
}
