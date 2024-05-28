package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.mapper.ProductMapper;
import com.adkp.fuexchange.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

        @Autowired
        public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
            this.productRepository = productRepository;
            this.productMapper = productMapper;
        }

    @Override
    public List<ProductDTO> viewMoreProduct(int current) {
        Pageable currentProduct = PageRequest.of(0, current);
        return productMapper.toProductDTOList(productRepository.topProduct(currentProduct));
    }

    @Override
    public long countTotalPostProduct() {
        return productRepository.count();
    }
}
