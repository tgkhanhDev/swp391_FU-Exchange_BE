package com.adkp.fuexchange.service;

import com.adkp.fuexchange.dto.CartPostDTO;
import com.adkp.fuexchange.mapper.CartPostMapper;
import com.adkp.fuexchange.pojo.*;
import com.adkp.fuexchange.repository.CartPostRepository;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.repository.RegisteredStudentRepository;
import com.adkp.fuexchange.repository.VariationDetailRepository;
import com.adkp.fuexchange.request.CartRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<CartPostDTO> viewCartPostProductByStudentId(Integer registeredStudentId) {

        List<CartPost> cartPostDTO = cartPostRepository.getCartProductByRegisteredStudentId(registeredStudentId);

        return cartPostMapper.toCartPostDTOList(cartPostDTO);
    }

    @Override
    @Transactional
    public List<CartPost> addToCart(CartRequest cartRequest) {

        RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(cartRequest.getRegisteredStudentId());

        Cart cart = registeredStudent.getCartId();

        PostProduct postProduct = postProductRepository.getReferenceById(cartRequest.getPostProductId());

        List<CartPost> cartPostsSaved = cartPostRepository.getCartPostByAllId(
                cart.getCartId(),
                postProduct.getPostProductId(),
                cartRequest.getVariationDetailId()
        );

        boolean validate = addToCartValidate(cartPostsSaved, cartRequest);

        List<CartPost> cartPosts;

        if (validate) {
            cartPosts = saveAlreadyExistPostInCart(cartRequest, cartPostsSaved);

            return cartPostRepository.saveAll(cartPosts);
        }

        cartPosts = saveNewPostInCart(cartRequest, cart, postProduct);

        return cartPostRepository.saveAll(cartPosts);
    }

    private List<CartPost> saveAlreadyExistPostInCart(CartRequest cartRequest, List<CartPost> cartPostsSaved) {

        ArrayList<CartPost> cartPosts = new ArrayList<>();

        CartPost previousCartPost = null;

        for (CartPost currentCartPost : cartPostsSaved) {

            if (cartRequest.getVariationDetailId().size() == 1) {
                cartPosts.add(
                        CartPost.builder()
                                .sttPostInCart(currentCartPost.getSttPostInCart())
                                .cartId(currentCartPost.getCartId())
                                .postProductId(currentCartPost.getPostProductId())
                                .variationDetailId(currentCartPost.getVariationDetailId())
                                .quantity(cartRequest.getQuantity() + currentCartPost.getQuantity())
                                .build()
                );
                cartPostRepository.delete(currentCartPost);
            } else if (previousCartPost != null &&
                    currentCartPost.getSttPostInCart() == previousCartPost.getSttPostInCart() &&
                    currentCartPost.getVariationDetailId().getVariationDetailId() != previousCartPost.getVariationDetailId().getVariationDetailId()
            ) {
                cartPosts.add(
                        CartPost.builder()
                                .sttPostInCart(previousCartPost.getSttPostInCart())
                                .cartId(previousCartPost.getCartId())
                                .postProductId(previousCartPost.getPostProductId())
                                .variationDetailId(previousCartPost.getVariationDetailId())
                                .quantity(cartRequest.getQuantity() + previousCartPost.getQuantity())
                                .build()
                );
                cartPosts.add(
                        CartPost.builder()
                                .sttPostInCart(currentCartPost.getSttPostInCart())
                                .cartId(currentCartPost.getCartId())
                                .postProductId(currentCartPost.getPostProductId())
                                .variationDetailId(currentCartPost.getVariationDetailId())
                                .quantity(cartRequest.getQuantity() + currentCartPost.getQuantity())
                                .build()
                );
                cartPostRepository.delete(previousCartPost);
                cartPostRepository.delete(currentCartPost);
            }

            previousCartPost = currentCartPost;
        }

        return cartPosts;
    }

    private List<CartPost> saveNewPostInCart(CartRequest cartRequest, Cart cart, PostProduct postProduct) {

        List<CartPost> cartPosts = new ArrayList<>();

        int sttPostInCart = cartPostRepository.getSttLastCart(cart.getCartId()) + 1;

        List<VariationDetail> variationDetails =
                variationDetailRepository.findAllById(cartRequest.getVariationDetailId());

        for (VariationDetail variationDetail : variationDetails) {
            cartPosts.add(
                    CartPost.builder()
                            .sttPostInCart(sttPostInCart)
                            .cartId(cart)
                            .postProductId(postProduct)
                            .variationDetailId(variationDetail)
                            .quantity(cartRequest.getQuantity())
                            .build()
            );
        }

        return cartPosts;
    }

    private boolean addToCartValidate(List<CartPost> cartPostsSaved, CartRequest cartRequest) {

        Map<Integer, List<Integer>> map = new HashMap<>();

        List<Integer> variationDetail = new ArrayList<>();

        CartPost previousCartPost = null;

        for (CartPost currentCartPost : cartPostsSaved) {
            if (cartRequest.getVariationDetailId().size() == 1) {
                variationDetail.add(currentCartPost.getVariationDetailId().getVariationDetailId());

                map.put(currentCartPost.getSttPostInCart(), variationDetail);
            } else if (previousCartPost != null &&
                    currentCartPost.getSttPostInCart() == previousCartPost.getSttPostInCart() &&
                    currentCartPost.getVariationDetailId().getVariationDetailId() != previousCartPost.getVariationDetailId().getVariationDetailId()
            ) {

                variationDetail.clear();

                variationDetail.add(previousCartPost.getVariationDetailId().getVariationDetailId());

                variationDetail.add(currentCartPost.getVariationDetailId().getVariationDetailId());

                map.put(currentCartPost.getSttPostInCart(), variationDetail);
            }

            previousCartPost = currentCartPost;
        }

        return map.containsValue(cartRequest.getVariationDetailId());
    }

    @Override
    @Transactional
    public boolean removeFromCart(CartRequest cartRequest) {

        RegisteredStudent registeredStudent = registeredStudentRepository.getReferenceById(cartRequest.getRegisteredStudentId());

        Cart cart = registeredStudent.getCartId();

        PostProduct postProduct = postProductRepository.getPostProductByPostId(cartRequest.getPostProductId());

        List<CartPost> cartPostsSaved = cartPostRepository.getCartPostByAllId(
                cart.getCartId(),
                postProduct.getPostProductId(),
                cartRequest.getVariationDetailId()
        );

        return deleteAlgorithm(cartPostsSaved, cartRequest);
    }

    private boolean deleteAlgorithm(List<CartPost> cartPostsSaved, CartRequest cartRequest) {

        if (cartPostsSaved.isEmpty()) {
            return false;
        }

        CartPost previousCartPost = null;

        for (CartPost currentCartPost : cartPostsSaved) {

            if (cartRequest.getVariationDetailId().size() == 1) {
                cartPostRepository.delete(currentCartPost);
            } else if (previousCartPost != null &&
                    currentCartPost.getSttPostInCart() == previousCartPost.getSttPostInCart() &&
                    currentCartPost.getVariationDetailId().getVariationDetailId() != previousCartPost.getVariationDetailId().getVariationDetailId()
            ) {
                cartPostRepository.delete(previousCartPost);
                cartPostRepository.delete(currentCartPost);
            }

            previousCartPost = currentCartPost;
        }
        return true;
    }
}
