package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.repository.PostProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostProductServiceImpl implements PostProductService{

    private final PostProductRepository postProductRepository;

    private final PostProductMapper postProductMapper;

    @Autowired
    public PostProductServiceImpl(PostProductRepository postProductRepository, PostProductMapper postProductMapper) {
        this.postProductRepository = postProductRepository;
        this.postProductMapper = postProductMapper;
    }

    @Override
    public List<PostProductDTO> viewMorePostProduct(int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        return postProductMapper.toPostProductDTOList(postProductRepository.viewMorePostProduct(currentProduct));
    }

    @Override
    public List<PostProductDTO> viewPostProductByCampus(int campusId) {
        return postProductMapper.toPostProductDTOList(postProductRepository.viewPostProductByCampus(campusId));
    }

    @Override
    public long countTotalPostProduct() {
        return postProductRepository.count();
    }
}
