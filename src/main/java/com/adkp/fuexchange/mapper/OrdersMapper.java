package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.adkp.fuexchange.pojo.Orders;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrdersMapper {
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "registeredStudentId", target = "registeredStudentId")
    @Mapping(source = "orderStatusId", target = "orderStatusId")
    @Mapping(source = "createDate", target = "createDate")
    @Mapping(source = "completeDate", target = "completeDate")
    @Mapping(source = "description", target = "description")
    OrdersDTO toOrdersDTO(Orders orders);

    @InheritInverseConfiguration(name = "toOrdersDTO")
    Orders toOrders(OrdersDTO ordersDTO);

}
