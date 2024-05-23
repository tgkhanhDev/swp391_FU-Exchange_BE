package com.adkp.fuexchange.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ResponseObject<T> {

    private HttpStatus status;

    private String message;

}
