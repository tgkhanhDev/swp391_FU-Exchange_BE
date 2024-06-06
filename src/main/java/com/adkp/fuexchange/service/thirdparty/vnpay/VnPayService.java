package com.adkp.fuexchange.service.thirdparty.vnpay;

import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.request.PostProductRequest;
import com.adkp.fuexchange.utils.Utils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VnPayService {

    private final HttpSession session;

    private final Utils utils;
    @Autowired
    public VnPayService(HttpSession session, Utils utils) {
        this.session = session;
        this.utils = utils;
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
                    currentProduct.getPostProductId() == previousProduct.getPostProductId() &&
                    currentProduct.getVariationDetailId() != previousProduct.getVariationDetailId()) {
                continue;
            } else {
                totalPrice += currentProduct.getPrice() * currentProduct.getQuantity();
            }
            previousProduct = currentProduct;
        }
        return totalPrice;
    }

}
