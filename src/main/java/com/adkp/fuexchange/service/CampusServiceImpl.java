package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.CampusMapper;
import com.adkp.fuexchange.repository.CampusRepository;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CampusServiceImpl implements CampusService {
    private final CampusRepository campusRepository;

    private final CampusMapper campusMapper;

    @Autowired
    public CampusServiceImpl(CampusRepository campusRepository, CampusMapper campusMapper) {
        this.campusRepository = campusRepository;
        this.campusMapper = campusMapper;
    }

    @Override
    public ResponseObject<Object> viewAllCampus() {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem campus thành công!")
                .data(
                        campusMapper.toCampusDTOList(campusRepository.findAllCampus())
                )
                .build();
    }
}
