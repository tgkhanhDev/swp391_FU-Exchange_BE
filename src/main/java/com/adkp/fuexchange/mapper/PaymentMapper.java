package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.PaymentDTO;
import com.adkp.fuexchange.pojo.Payment;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrdersMapper.class, PaymentMapper.class}
)
public interface PaymentMapper {

    @Mapping(source = "paymentId", target = "paymentId")
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "paymentMethodId", target = "paymentMethod")
    @Mapping(source = "paymentStatus", target = "paymentStatus")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "transactionId", target = "transaction")
    PaymentDTO toPaymentDTO(Payment payment);

    @InheritInverseConfiguration(name = "toPaymentDTO")
    Payment toPayment(PaymentDTO paymentDTO);

    List<PaymentDTO> toPaymentDTOList(List<Payment> paymentList);

    List<Payment> toPaymentList(List<PaymentDTO> paymentDTOList);
}
