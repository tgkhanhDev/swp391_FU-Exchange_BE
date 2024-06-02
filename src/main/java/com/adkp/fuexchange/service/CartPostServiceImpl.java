package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.PostProductMapper;
import com.adkp.fuexchange.pojo.Cart;
import com.adkp.fuexchange.pojo.CartPost;
import com.adkp.fuexchange.pojo.CartPostEmbeddable;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.repository.CartPostRepository;
import com.adkp.fuexchange.repository.CartRepository;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
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

    @Autowired
    public CartPostServiceImpl(CartPostRepository cartPostRepository, CartRepository cartRepository, PostProductRepository postProductRepository, RegisteredStudentRepository registeredStudentRepository,  PostProductMapper postProductMapper) {
        this.cartPostRepository = cartPostRepository;
        this.cartRepository = cartRepository;
        this.postProductRepository = postProductRepository;
        this.registeredStudentRepository = registeredStudentRepository;
        this.postProductMapper = postProductMapper;
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
        Cart c = cartRepository.getCartByRegisterdStudentId(regisId);
        PostProduct pp = postProductRepository.getReferenceById(cartRequest.getPostProductId());

        cartPostRepository.save(
                CartPost.builder()
                        .cartPostId(new CartPostEmbeddable(c.getCartId(), cartRequest.getPostProductId()))
                        .cartId(c)
                        .postProductId(pp)
                        .build()
        );

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Thêm giỏ hàng thành công")
                .build();
    }

}
