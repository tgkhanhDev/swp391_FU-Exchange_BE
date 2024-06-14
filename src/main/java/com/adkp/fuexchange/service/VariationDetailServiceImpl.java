package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.VariationDetail;
import com.adkp.fuexchange.repository.VariationDetailRepository;
import com.adkp.fuexchange.repository.VariationRepository;
import com.adkp.fuexchange.request.VariationDetailRequest;
import com.adkp.fuexchange.response.RegisterVariationDetailResponse;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class VariationDetailServiceImpl implements  VariationDetailService{

    private  final VariationRepository variationRepository;
    private  final VariationDetailRepository variationDetailRepository;
@Autowired
    public VariationDetailServiceImpl(VariationRepository variationRepository, VariationDetailRepository variationDetailRepository) {
        this.variationRepository = variationRepository;
    this.variationDetailRepository = variationDetailRepository;
}


    @Override
    @Transactional
    public ResponseObject<Object> CreateNewVariationByVariationID(VariationDetailRequest variationDetailRequest) {
        VariationDetail variationDetail = new VariationDetail(variationRepository.getReferenceById(variationDetailRequest.getVariationId()),variationDetailRequest.getDescription());
        variationDetailRepository.save(variationDetail);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value()).
                message(HttpStatus.OK.name()).
                content("tạo thông tin  variation detail  thành công")
                .data(new RegisterVariationDetailResponse(variationDetail.getVariationDetailId(),variationDetail.getVariationId().getVariationId(),variationDetail.getDescription())).build();
    }

    @Override
    public void deleteVariationDetailByID(int variationDetailId) {

        variationDetailRepository.deleteById(variationDetailId);

    }
}
