package com.adkp.fuexchange.service.thirdparty.vnpay;

import com.adkp.fuexchange.pojo.PostProduct;
import com.adkp.fuexchange.repository.PostProductRepository;
import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.request.PostProductRequest;
import com.adkp.fuexchange.utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VnPayService {

    private final HttpSession session;

    private final Utils utils;

    private final PostProductRepository postProductRepository;

    @Autowired
    public VnPayService(HttpSession session, Utils utils, PostProductRepository postProductRepository) {
        this.session = session;
        this.utils = utils;
        this.postProductRepository = postProductRepository;
    }

    public VnPayResponse vnPayPayment(OrdersRequest ordersRequest, HttpHeaders headers) {
        long amount = totalPrice(ordersRequest.getPostProductToBuyRequests()) * 100L;
        String bankCode = "NCB";
        Map<String, String> vnpParamsMap = VnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VnPayUtils.getIpAddress(headers));
        vnpParamsMap.put("vnp_BankCode", bankCode);
        String queryUrl = VnPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtils.hmacSHA512(VnPayUtils.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html" + "?" + queryUrl;
        session.setAttribute("ordersRequest", ordersRequest);
        return VnPayResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .paymentUrl(paymentUrl)
                .build();
    }

    public boolean vnPayPaymentCallBack(String vnp_ResponseCode) {
        if (vnp_ResponseCode.equals("00")) {
            OrdersRequest ordersRequest = (OrdersRequest) session.getAttribute("ordersRequest");
            session.removeAttribute("ordersRequest");
            utils.navigationDataAsyncForAnotherMethod("http://localhost:8080/order/payment/pay-order", ordersRequest, HttpMethod.POST);
            return true;
        }
        session.removeAttribute("ordersRequest");
        return false;
    }

    private long totalPrice(List<PostProductRequest> postProductRequestList) {
        long totalPrice = 0;

        PostProductRequest previousProduct = null;

        for (PostProductRequest currentProduct : postProductRequestList) {

            if (previousProduct != null &&
                    currentProduct.getSttOrder() != previousProduct.getSttOrder()
            ) {
                totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();
            } else if (previousProduct == null) {

                totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();

            }
            previousProduct = currentProduct;
        }

        return totalPrice;
    }

    public void validateQuantity(List<PostProductRequest> postProductRequestList) {

        Map<Integer, Integer> quantityEachPost = calcQuantityEachPost(postProductRequestList);

        List<Integer> postProductId = new ArrayList<>();

        quantityEachPost.forEach((key, value) -> {
            postProductId.add(key);
        });

        List<PostProduct> postProductList = postProductRepository.findAllById(postProductId);

        for (PostProduct postProduct : postProductList) {
            int quantity = quantityEachPost.get(postProduct.getPostProductId());

            if ((postProduct.getQuantity() - quantity) < 0) {
                throw new DataIntegrityViolationException("Số lượng mà bạn muốn mua đã vượt quá giới hạn mà sản phẩm hiện có!");
            }

        }

    }

    private Map<Integer, Integer> calcQuantityEachPost(List<PostProductRequest> postProductRequestList) {

        Map<Integer, Integer> quantityEachPost = new HashMap<>();

        PostProductRequest previousProduct = null;

        for (PostProductRequest currentProduct : postProductRequestList) {
            if (previousProduct != null &&
                    currentProduct.getSttOrder() == previousProduct.getSttOrder()
            ) {
                if (currentProduct.getPostProductId() != previousProduct.getPostProductId()) {
                    quantityEachPost.merge(currentProduct.getPostProductId(), currentProduct.getQuantity(), Integer::sum);
                }
            } else {
                quantityEachPost.merge(currentProduct.getPostProductId(), currentProduct.getQuantity(), Integer::sum);
            }
            previousProduct = currentProduct;
        }

        return quantityEachPost;
    }

}
