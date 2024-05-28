package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.repository.PostProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        String campus = Optional.ofNullable(campusId).map(String::valueOf).orElse("");
        String postType = Optional.ofNullable(postTypeId).map(String::valueOf).orElse("");
        String nameProduct = Optional.ofNullable(name).map(String::valueOf).orElse("");

        return postProductMapper.toPostProductDTOList(
            postProductRepository.filterPostProduct(currentProduct, campus, postType, nameProduct)
        );
    }

    @Override
    public PostProductDTO getPostProductById(int postProductId) {
        return postProductMapper.toPostProductDTO(postProductRepository.getPostProductByPostId(postProductId));
    }

    @Override
        public long countPostProduct(Integer campusId, Integer postTypeId, String name, List<PostProductDTO> productDTOList) {
        if (campusId == null && postTypeId == null && (name == null || name.isEmpty())) {
            return postProductRepository.count();
        }
        return productDTOList.size();

    }


}
