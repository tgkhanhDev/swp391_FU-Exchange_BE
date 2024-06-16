package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.UpdatePostProductRequest;
import com.adkp.fuexchange.response.MetaResponse;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
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

    private final ProductRepository productRepository;

    private final PostTypeRepository postTypeRepository;

    private final PostStatusRepository postStatusRepository;

    private final CampusRepository campusRepository;

    @Autowired
    public PostProductServiceImpl(PostProductRepository postProductRepository, PostProductMapper postProductMapper, ProductRepository productRepository, PostTypeRepository postTypeRepository, PostStatusRepository postStatusRepository, CampusRepository campusRepository) {
        this.postProductRepository = postProductRepository;
        this.postProductMapper = postProductMapper;
        this.productRepository = productRepository;
        this.postTypeRepository = postTypeRepository;
        this.postStatusRepository = postStatusRepository;
        this.campusRepository = campusRepository;
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

    @Override
    @Transactional
    public PostProductDTO updatePostProduct(UpdatePostProductRequest updatePostProductRequest) {
        PostProduct postProduct = postProductRepository.getReferenceById(updatePostProductRequest.getPostProductId());

        return postProductMapper.toPostProductDTO(
                postProductRepository.save(
                        PostProduct.builder()
                                .productId(productRepository.getReferenceById(updatePostProductRequest.getProductId()))
                                .postTypeId(postTypeRepository.getReferenceById(updatePostProductRequest.getPostTypeId()))
                                .campusId(campusRepository.getReferenceById(updatePostProductRequest.getCampusId()))
                                .postStatusId(postStatusRepository.getReferenceById(updatePostProductRequest.getPostStatusId()))
                                .createDate(updatePostProductRequest.getCreateDate())
                                .quantity(updatePostProductRequest.getQuantity())
                                .content(updatePostProductRequest.getContent())
                                .build()
                )
        );
    }

    public long countPostProduct(Integer campusId, Integer postTypeId, String name, Integer categoryId, List<PostProductDTO> postProductDTOList) {
        if (campusId == null && postTypeId == null && (name == null || name.isEmpty())) {
            return postProductRepository.count();
        }
        return postProductDTOList.size();
    }


}
