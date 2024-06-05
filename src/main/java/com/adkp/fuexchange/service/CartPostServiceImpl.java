package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.*;
import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.ResponseObject;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CartPostServiceImpl implements CartPostService {
    private final CartPostRepository cartPostRepository;
    private final CartRepository cartRepository;
    private final PostProductRepository postProductRepository;
    private final RegisteredStudentRepository registeredStudentRepository;
    private final PostProductMapper postProductMapper;

    private final VariationDetailRepository variationDetailRepository;

    @Autowired
    public CartPostServiceImpl(CartPostRepository cartPostRepository, CartRepository cartRepository, PostProductRepository postProductRepository, RegisteredStudentRepository registeredStudentRepository, PostProductMapper postProductMapper, VariationDetailRepository variationDetailRepository) {
        this.cartPostRepository = cartPostRepository;
        this.cartRepository = cartRepository;
        this.postProductRepository = postProductRepository;
        this.registeredStudentRepository = registeredStudentRepository;
        this.postProductMapper = postProductMapper;
        this.variationDetailRepository = variationDetailRepository;
    }

    @Override
    public ResponseObject<Object> viewCartPostProductByStudentId(String studentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        postProductMapper.toPostProductDTOList(cartPostRepository.getCartProductByStudentId(studentId))
                )
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> addToCart(CartRequest cartRequest) {
        int regisId = registeredStudentRepository.findRegisteredStudentByStudentId(cartRequest.getStudentId()).getRegisteredStudentId();
        Cart cart = cartRepository.getCartByRegisterdStudentId(regisId);
        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());
        VariationDetail variationDetail = variationDetailRepository.getReferenceById(cartRequest.getVariationId());
        cartPostRepository.save(
                CartPost.builder()
                        .cartPostId(new CartPostEmbeddable(cart.getCartId(), cartRequest.getPostProductId(), cartRequest.getVariationId()))
                        .cartId(cart)
                        .postProductId(postProduct)
                        .variationDetailId(variationDetail)
                        .quantity(cartRequest.getQuantity())
                        .build()
        );

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Thêm giỏ hàng thành công")
                .build();
    }

}
