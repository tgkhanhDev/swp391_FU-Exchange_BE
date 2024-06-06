package com.adkp.fuexchange.mapper;

import com.adkp.fuexchange.dto.CartDTO;
import com.adkp.fuexchange.pojo.Cart;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "cartId", target = "cartId")
    @Mapping(source = "registeredStudentId.registeredStudentId", target = "registeredStudentId")
    CartDTO toCartDTO(Cart cart);

    @InheritInverseConfiguration(name = "toCartDTO")
    Cart toCart(CartDTO cartDTO);

    List<CartDTO> toCartDTOList(List<Cart> cartList);

    List<Cart> toCartList(List<CartDTO> cartDTOList);
}
