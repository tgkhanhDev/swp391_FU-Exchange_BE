package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.mapper.CampusMapper;
import com.adkp.fuexchange.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CampusServiceImpl implements  CampusService{
    private final CampusRepository campusRepository;

    private final CampusMapper campusMapper;

    @Autowired
    public CampusServiceImpl(CampusRepository campusRepository, CampusMapper campusMapper) {
        this.campusRepository = campusRepository;
        this.campusMapper = campusMapper;
    }

    @Override
    public List<CampusDTO> viewAllCampus() {
        return campusMapper.toCampusDTOList(campusRepository.findAllCampus());
    }
}
