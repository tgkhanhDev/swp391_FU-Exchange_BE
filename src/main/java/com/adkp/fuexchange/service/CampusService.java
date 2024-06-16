package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.Campus;
import com.adkp.fuexchange.request.CampusRequest;
import com.adkp.fuexchange.response.ResponseObject;

public interface CampusService {
    ResponseObject<Object> viewAllCampus();

    Campus addCampus(CampusRequest campusRequest);
}
