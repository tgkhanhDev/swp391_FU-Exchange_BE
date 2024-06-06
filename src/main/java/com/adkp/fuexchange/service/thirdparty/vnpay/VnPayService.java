package com.adkp.fuexchange.service.thirdparty.vnpay;

import com.adkp.fuexchange.request.OrdersRequest;
import com.adkp.fuexchange.request.PostProductRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class VnPayService {

    private final HttpSession session;

    private final RestTemplate restTemplate;

    @Autowired
    public VnPayService(HttpSession session, RestTemplate restTemplate) {
        this.session = session;
        this.restTemplate = restTemplate;
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
            navigationToSaveOrderAsync(ordersRequest);
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

    @Async
    public void navigationToSaveOrderAsync(OrdersRequest ordersRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<OrdersRequest> ordersRequest1 = new HttpEntity<>(ordersRequest, headers);

        String response = restTemplate
                .exchange(
                        "http://localhost:8080/order/payment/pay-order",
                        HttpMethod.POST,
                        ordersRequest1,
                        String.class)
                .getBody();

        CompletableFuture.completedFuture(response);
    }
}
