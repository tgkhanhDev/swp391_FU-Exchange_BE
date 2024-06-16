package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.mapper.CartPostMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.CartPostRepository;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.VariationDetailRepository;
import com.adkp.fuexchange.request.CartRequest;
import com.adkp.fuexchange.response.CartPostResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.VariationResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartPostServiceImpl implements CartPostService {
    private final CartPostRepository cartPostRepository;

    private final PostProductRepository postProductRepository;

    private final RegisteredStudentRepository registeredStudentRepository;

    private final CartPostMapper cartPostMapper;

    private final VariationDetailRepository variationDetailRepository;

    @Autowired
    public CartPostServiceImpl(CartPostRepository cartPostRepository, PostProductRepository postProductRepository, RegisteredStudentRepository registeredStudentRepository, CartPostMapper cartPostMapper, VariationDetailRepository variationDetailRepository) {
        this.cartPostRepository = cartPostRepository;
        this.postProductRepository = postProductRepository;
        this.registeredStudentRepository = registeredStudentRepository;
        this.cartPostMapper = cartPostMapper;
        this.variationDetailRepository = variationDetailRepository;
    }


    @Override
    public ResponseObject<Object> viewCartPostProductByStudentId(String studentId) {

//        CartPostDTO cartPostDTO = cartPostMapper.toCartPostDTOList(cartPostRepository.getCartProductByStudentId(studentId));
//
//        CartPostResponse cartPostResponse = CartPostResponse.builder()
//                .productDetailId()
//                .build();

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
    public ResponseObject<Object> viewCartPostById(CartPostEmbeddable cartPostId) {

//        return ResponseObject.builder()
//                .status(HttpStatus.OK.value())
//                .message(HttpStatus.OK.name())
//                .content("Xem thông tin thành công!")
//                .data(
//                        postProductMapper.toPostProductDTOList(cartPostRepository.getCartProductByStudentId(studentId))
//                )
//                .build();
        return null;
    }

    private List<VariationResponse> getVariation(List<VariationDetail> variationDetails, List<Integer> variationIds) {

        List<VariationResponse> variations = new ArrayList<>();

        for (VariationDetail detail : variationDetails) {
            variationIds.add(detail.getVariationId().getVariationId());

            VariationResponse variation = VariationResponse.builder()
                    .variationId(detail.getVariationId().getVariationId())
                    .variationName(detail.getVariationId().getVariationName())
                    .variationDetail(detail)
                    .build();

            detail.getVariationId().setProductId(null);
            detail.setVariationId(null);

            variations.add(variation);
        }
        return variations;
    }

    @Override
    @Transactional
    public ResponseObject<Object> addToCart(CartRequest cartRequest) {



        RegisteredStudent registeredStudent = registeredStudentRepository.findRegisteredStudentByStudentId(cartRequest.getStudentId());
        if (registeredStudent == null) {
            return ResponseObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(HttpStatus.NOT_FOUND.name())
                    .content("Người dùng không tồn tại")
                    .build();
        }

        Cart cart = registeredStudent.getCartId();
        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());
//        VariationDetail variationDetail = variationDetailRepository.getReferenceById(cartRequest.getVariationId());
        VariationDetail variationDetail = null;
//======
        cartPostRepository.save(
                CartPost.builder()
//                        .cartPostId(new CartPostEmbeddable(cart.getCartId(), cartRequest.getPostProductId(), cartRequest.getVariationId()))
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
    public ResponseObject<Object> updateCart(CartRequest cartRequest) {


        RegisteredStudent registeredStudent = registeredStudentRepository.findRegisteredStudentByStudentId(cartRequest.getStudentId());
        if (registeredStudent == null) {
            return ResponseObject.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(HttpStatus.NOT_FOUND.name())
                    .content("Người dùng không tồn tại")
                    .build();
        }

        Cart cart = registeredStudent.getCartId();
        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());
//        VariationDetail variationDetail = variationDetailRepository.getReferenceById(cartRequest.getVariationId());


        VariationDetail variationDetail = null;
//======



        cartPostRepository.save(
                CartPost.builder()
//                        .cartPostId(new CartPostEmbeddable(cart.getCartId(), cartRequest.getPostProductId(), cartRequest.getVariationId()))
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
    public ResponseObject<Object> removeFromCart(CartPostEmbeddable cartPostId) {

        CartPost cartpost = cartPostRepository.getCartPostById(cartPostId.getCartId(), cartPostId.getPostProductId(), cartPostId.getVariationDetailId());
        if (cartpost == null) {
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
