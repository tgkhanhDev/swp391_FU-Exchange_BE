package com.adkp.fuexchange.service;

import com.adkp.fuexchange.pojo.OrderPostProduct;
import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.repository.OrderPostProductRepository;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.repository.ProductRepository;
import com.adkp.fuexchange.response.OrderPostProductResponse;
import com.adkp.fuexchange.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderPostProductServiceImpl implements OrderPostProductService {

    private final PostProductRepository postProductRepository;
    private final OrderPostProductRepository orderPostProductRepository;
    private final ProductRepository productRepository;

    public OrderPostProductServiceImpl(PostProductRepository postProductRepository, OrderPostProductRepository orderPostProductRepository, ProductRepository productRepository) {
        this.postProductRepository = postProductRepository;
        this.orderPostProductRepository = orderPostProductRepository;
        this.productRepository = productRepository;
    }


    @Override
    public ResponseObject<Object> viewTotalPriceEachPostProductBySellerID(int sellerID) {
        List<PostProduct> postProductList = postProductRepository.getPostProductBySellerID(sellerID);
        List<OrderPostProduct> productResponseList = new ArrayList<>();
        List<OrderPostProductResponse>  OrderPostProductResponse =  new ArrayList<>();
        long totalPrice = 0;
        for (PostProduct postProduct : postProductList) {

            List<OrderPostProduct> orderPostProductList = orderPostProductRepository.getOrdersPostProductId(postProduct.getPostProductId());
            for (OrderPostProduct orderPostProduct : orderPostProductList){
                if(orderPostProduct.getPostProductId().getPostProductId()==postProduct.getPostProductId()){
                    totalPrice+=orderPostProduct.getPriceBought();
                }
            }
            if(totalPrice>0){

                    OrderPostProductResponse.add(new OrderPostProductResponse().builder()
                            .postProductID(postProduct.getPostProductId()).totalpriceBought(totalPrice)
                            .build());
                totalPrice = 0;
            }
        }


        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Tổng doanh thu bài đăng của người dùng!")
                .data(OrderPostProductResponse)
                .build();
    }
}
