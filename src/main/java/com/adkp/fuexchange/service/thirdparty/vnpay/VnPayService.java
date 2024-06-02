package com.adkp.fuexchange.service.thirdparty.vnpay;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VnPayService {

    public VnPayResponse vnPayPayment(String amountRequest, HttpServletRequest request){
        long amount = Integer.parseInt(amountRequest) * 100L;
        String bankCode = "NCB";
        Map<String, String> vnpParamsMap = VnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_IpAddr", VnPayUtils.getIpAddress(request));
        vnpParamsMap.put("vnp_BankCode", bankCode);
        String queryUrl = VnPayUtils.getPaymentURL(vnpParamsMap, true);
        String hashData = VnPayUtils.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnPayUtils.hmacSHA512(VnPayUtils.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html" + "?" + queryUrl;
        return VnPayResponse.builder()
                .code(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .paymentUrl(paymentUrl)
                .build();
    }

    public VnPayResponse vnPayPaymentCallBack(HttpServletRequest request){
        if (request.getParameter("vnp_ResponseCode").equals("00")){

            return VnPayResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message(HttpStatus.OK.name())
                    .content("Giao dịch thành công!")
                    .build();
        } else {
            return VnPayResponse.builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message(HttpStatus.BAD_REQUEST.name())
                    .content("Giao dịch thất bại!")
                    .build();
        }
    }
}
