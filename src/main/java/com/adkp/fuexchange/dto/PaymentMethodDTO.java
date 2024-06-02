package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Payment;
import lombok.Data;

import java.util.List;
@Data
public class PaymentMethodDTO {

    private int paymentMethodId;

    private String paymentMethodName;

    private List<Payment> payment;
}
