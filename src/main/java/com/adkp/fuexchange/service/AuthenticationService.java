package com.adkp.fuexchange.service;

import com.adkp.fuexchange.request.LoginRequest;
import com.adkp.fuexchange.request.RegisterRequest;
import com.adkp.fuexchange.response.LoginResponse;
import com.adkp.fuexchange.response.ResponseObject;

public interface AuthenticationService {

    LoginResponse loginResponse(LoginRequest loginRequest);

    ResponseObject registerResponse(RegisterRequest registerRequest);
}
