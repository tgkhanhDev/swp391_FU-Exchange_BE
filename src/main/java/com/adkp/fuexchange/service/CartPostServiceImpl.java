package com.adkp.fuexchange.service;

import com.adkp.fuexchange.mapper.CartPostMapper;
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

    private final CartPostMapper cartPostMapper;

    private final VariationDetailRepository variationDetailRepository;

    @Autowired
    public CartPostServiceImpl(CartPostRepository cartPostRepository, CartRepository cartRepository, PostProductRepository postProductRepository, RegisteredStudentRepository registeredStudentRepository, PostProductMapper postProductMapper, CartPostMapper cartPostMapper, VariationDetailRepository variationDetailRepository) {
        this.cartPostRepository = cartPostRepository;
        this.cartRepository = cartRepository;
        this.postProductRepository = postProductRepository;
        this.registeredStudentRepository = registeredStudentRepository;
        this.postProductMapper = postProductMapper;
        this.cartPostMapper = cartPostMapper;
        this.variationDetailRepository = variationDetailRepository;
    }

    @Override
    public ResponseObject<Object> viewCartPostProductByStudentId(String studentId) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xem thông tin thành công!")
                .data(
                        cartPostMapper.toCartPostDTOList(cartPostRepository.getCartProductByStudentId(studentId))
                )
                .build();
    }

    @Override
    public ResponseObject<Object> viewCartPostById(CartPostEmbeddable cartPostId){

//        return ResponseObject.builder()
//                .status(HttpStatus.OK.value())
//                .message(HttpStatus.OK.name())
//                .content("Xem thông tin thành công!")
//                .data(
//                        postProductMapper.toPostProductDTOList(cartPostRepository.getCartProductByStudentId(studentId))
//                )
//                .build();
        return  null;
    }


    @Override
    @Transactional
    public ResponseObject<Object> addToCart(CartRequest cartRequest) {
        RegisteredStudent registeredStudent = registeredStudentRepository.findRegisteredStudentByStudentId(cartRequest.getStudentId());
        if(registeredStudent == null){
            return ResponseObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(HttpStatus.NOT_FOUND.name())
                    .content("Người dùng không tồn tại")
                    .build();
        }

        Cart cart = registeredStudent.getCartId();
        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());
        VariationDetail variationDetail = variationDetailRepository.getReferenceById(cartRequest.getVariationId());

        cartPostRepository.save(
                CartPost.builder()
                        .cartPostId(new CartPostEmbeddable(cart.getCartId(), cartRequest.getPostProductId(), cartRequest.getVariationId()))
                        .cartId(registeredStudent.getCartId())
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

    @Override
    @Transactional
    public ResponseObject<Object> updateCart(CartRequest cartRequest){

        RegisteredStudent registeredStudent = registeredStudentRepository.findRegisteredStudentByStudentId(cartRequest.getStudentId());
        if(registeredStudent == null){
            return ResponseObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(HttpStatus.NOT_FOUND.name())
                    .content("Người dùng không tồn tại")
                    .build();
        }

        Cart cart = registeredStudent.getCartId();
        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());
        VariationDetail variationDetail = variationDetailRepository.getReferenceById(cartRequest.getVariationId());

        cartPostRepository.save(
                CartPost.builder()
                        .cartPostId(new CartPostEmbeddable(cart.getCartId(), cartRequest.getPostProductId(), cartRequest.getVariationId()))
                        .cartId(registeredStudent.getCartId())
                        .postProductId(postProduct)
                        .variationDetailId(variationDetail)
                        .quantity(cartRequest.getQuantity())
                        .build()
        );

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Chỉnh sửa giỏ hàng thành công")
                .build();
    }

    @Override
    @Transactional
    public ResponseObject<Object> removeFromCart(CartPostEmbeddable cartPostId){

        CartPost cartpost = cartPostRepository.getCartPostById(cartPostId.getCartId(), cartPostId.getPostProductId(), cartPostId.getVariationDetailId());
        if(cartpost == null){
            return ResponseObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(HttpStatus.NOT_FOUND.name())
                    .content("Vật phẩm không tồn tại")
                    .build();
        }
        cartPostRepository.delete(cartpost);

        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Xóa sản phẩm thành công")
                .build();
    }

}
