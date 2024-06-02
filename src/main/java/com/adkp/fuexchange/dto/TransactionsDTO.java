package com.adkp.fuexchange.dto;

import com.adkp.fuexchange.pojo.Payment;
import com.adkp.fuexchange.pojo.TransactionsStatus;
import lombok.Data;

import java.util.Date;
@Data
public class TransactionsDTO {

    private int transactionsId;

    private Payment payment;

    private TransactionsStatus transactionsStatus;

    private double totalPrice;

    private Date createTime;

    private Date completeTime;
}
