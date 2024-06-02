package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Orders;
import com.adkp.fuexchange.pojo.PaymentMethod;
import com.adkp.fuexchange.pojo.Transactions;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {
    private int paymentId;

    private Orders order;

    private PaymentMethod paymentMethod;

    private boolean paymentStatus;

    private Date createTime;

    private Transactions transaction;
}
