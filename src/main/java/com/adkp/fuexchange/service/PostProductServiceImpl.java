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
public class PostProductServiceImpl implements PostProductService {

    private final PostProductRepository postProductRepository;

    private final PostProductMapper postProductMapper;

    @Autowired
    public PostProductServiceImpl(PostProductRepository postProductRepository, PostProductMapper postProductMapper) {
        this.postProductRepository = postProductRepository;
        this.postProductMapper = postProductMapper;
    }

    @Override
    public List<PostProductDTO> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name) {
        Pageable currentProduct = PageRequest.of(0, current);
        if (campusId != null && postTypeId != null && name != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByAll(currentProduct, campusId, postTypeId, name)
            );
        } else if (campusId == null && postTypeId == null && name == null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.viewMorePostProduct(currentProduct)
            );
        } else if (campusId != null && name != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByCampusAndName(currentProduct, campusId, name)
            );
        } else if (campusId != null && postTypeId != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByCampusAndPostType(currentProduct, campusId, postTypeId)
            );
        } else if (name != null && postTypeId != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByPostTypeAndName(currentProduct, postTypeId, name)
            );
        } else if (campusId != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByCampus(currentProduct, campusId)
            );
        } else if (name != null) {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByName(currentProduct, name)
            );
        } else {
            return postProductMapper.toPostProductDTOList(
                    postProductRepository.filterPostProductByPostType(currentProduct, postTypeId)
            );
        }
    }

    @Override
    public long countTotalPostProduct() {
        return postProductRepository.count();
    }
}
