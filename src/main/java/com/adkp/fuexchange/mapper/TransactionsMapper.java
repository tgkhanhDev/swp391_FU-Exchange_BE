package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.TransactionsDTO;
import com.adkp.fuexchange.pojo.Transactions;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {TransactionsStatusMapper.class}
)
public interface TransactionsMapper {

    @Mapping(source = "transactionsId", target = "transactionsId")
    @Mapping(source = "paymentId", target = "payment")
    @Mapping(source = "transactionsStatusId", target = "transactionsStatus")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "createTime", target = "createTime")
    @Mapping(source = "completeTime", target = "completeTime")
    TransactionsDTO toTransactionsDTO(Transactions transactions);

    @InheritInverseConfiguration(name = "toTransactionsDTO")
    Transactions toTransactions(TransactionsDTO transactionsDTO);

    List<TransactionsDTO> toTransactionsDTOList(List<Transactions> transactionsList);

    List<Transactions> toTransactionsList(List<TransactionsDTO> transactionsDTOList);
}
