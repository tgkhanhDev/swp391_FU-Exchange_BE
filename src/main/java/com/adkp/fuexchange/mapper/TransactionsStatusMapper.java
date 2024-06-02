package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.TransactionsStatusDTO;
import com.adkp.fuexchange.pojo.TransactionsStatus;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapping;

import java.util.List;

public interface TransactionsStatusMapper {
    @Mapping(source = "transactionsStatusId", target = "transactionsStatusId")
    @Mapping(source = "transactionsStatusName", target = "transactionsStatusName")
    @Mapping(source = "transactionId", target = "transactionId")
    TransactionsStatusDTO toTransactionsStatusDTO(TransactionsStatus transactionsStatus);

    @InheritInverseConfiguration(name = "toTransactionsStatusDTO")
    TransactionsStatus toTransactionsStatus(TransactionsStatusDTO transactionsStatusDTO);

    List<TransactionsStatusDTO> toTransactionsStatusDTOList(List<TransactionsStatus> transactionsStatus);

    List<TransactionsStatus> toTransactionsStatusList(List<TransactionsStatusDTO> transactionsStatusDTOList);
}
