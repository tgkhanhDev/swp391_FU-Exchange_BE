package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.PaymentDTO;
import com.adkp.fuexchange.dto.PaymentMethodDTO;
import com.adkp.fuexchange.dto.TransactionsStatusDTO;
import com.adkp.fuexchange.pojo.Payment;
import com.adkp.fuexchange.pojo.PaymentMethod;
import com.adkp.fuexchange.pojo.TransactionsStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper {
    @Mapping(source = "paymentMethodId", target = "paymentMethodId")
    @Mapping(source = "paymentMethodName", target = "paymentMethodName")
    @Mapping(source = "paymentId", target = "payment")
    PaymentMethodDTO toPaymentMethodDTO(PaymentMethod paymentMethod);

    @InheritInverseConfiguration(name = "toPaymentMethodDTO")
    PaymentMethod toPaymentMethod(PaymentMethodDTO paymentMethodDTO);

    List<PaymentMethodDTO> toPaymentMethodDTOList(List<PaymentMethod> paymentMethodList);

    List<PaymentMethod> toPaymentMethodList(List<PaymentMethodDTO> paymentMethodDTOList);
}
