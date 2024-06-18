package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.SellerDTO;
import com.adkp.fuexchange.mapper.SellerMapper;
import com.adkp.fuexchange.pojo.Seller;
import com.adkp.fuexchange.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {

    private final SellerRepository sellerRepository;

    private final SellerMapper sellerMapper;

    @Autowired
    public ModeratorServiceImpl(SellerRepository sellerRepository, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
    }

    @Override
    public List<SellerDTO> viewRegisterToSellerRequest() {
        List<Seller> sellers = sellerRepository.viewRegisterToSellerRequest();
        return sellerMapper.toSellerDTOList(sellers);
    }
}
