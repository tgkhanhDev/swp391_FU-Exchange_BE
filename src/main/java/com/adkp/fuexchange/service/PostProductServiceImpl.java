package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.response.MetaResponse;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseObject<Object> viewMorePostProduct(int current, Integer campusId, Integer postTypeId, String name, Integer categoryId) {
        Pageable currentProduct = PageRequest.of(0, current);

        String campus = Optional.ofNullable(campusId).map(String::valueOf).orElse("");
        String postType = Optional.ofNullable(postTypeId).map(String::valueOf).orElse("");
        String nameProduct = Optional.ofNullable(name).map(String::valueOf).orElse("");
        String category = Optional.ofNullable(categoryId).map(String::valueOf).orElse("");

        List<PostProductDTO> postProductDTO = postProductMapper.toPostProductDTOList(
                postProductRepository.filterPostProduct(currentProduct, campus, postType, nameProduct, category)
        );
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thêm thành công!")
                .data(postProductDTO)
                .meta(new MetaResponse(countPostProduct(campusId, postTypeId, name, categoryId, postProductDTO), current))
                .build();
    }

    @Override
    public ResponseObject<Object> getPostProductById(int postProductId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        postProductMapper.toPostProductDTO(postProductRepository.getPostProductByPostId(postProductId))
                )
                .build();
    }

    public long countPostProduct(Integer campusId, Integer postTypeId, String name, Integer categoryId, List<PostProductDTO> postProductDTOList) {
        if (campusId == null && postTypeId == null && (name == null || name.isEmpty())) {
            return postProductRepository.count();
        }
        return postProductDTOList.size();
    }


}
