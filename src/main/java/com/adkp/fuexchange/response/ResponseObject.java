package com.adkp.fuexchange.response;

import com.adkp.fuexchange.dto.OrdersDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonInclude(JsonInclude.Include.NON_NULL) // Jackson không tuần tự hóa field null
public class ResponseObject {

    private int status;

    private String message;

    private String content;

    private InforLoginResponse data;

    private List<OrdersDTO> ordersDTO;
}
