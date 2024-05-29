package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.CheckInformationRequest;
import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.response.ResponseObject;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

    ResponseObject register(RegisterRequest registerRequest);

    ResponseObject checkInformationRegister(CheckInformationRequest checkInformationRequest);

}
