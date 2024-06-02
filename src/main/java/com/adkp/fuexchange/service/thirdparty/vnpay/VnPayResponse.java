package com.adkp.fuexchange.service.thirdparty.vnpay;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VnPayResponse {
    private int code;
    private String message;
    private String content;
    private String paymentUrl;
}
