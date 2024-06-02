package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.ProductMapper;
import com.adkp.fuexchange.repository.ProductRepository;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ResponseObject<Object> viewMoreProduct(int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thêm thành công!")
                .data(
                        productMapper.toProductDTOList(productRepository.topProduct(currentProduct))
                )
                .build();
    }

    @Override
    public long countTotalPostProduct() {
        return productRepository.count();
    }
}
