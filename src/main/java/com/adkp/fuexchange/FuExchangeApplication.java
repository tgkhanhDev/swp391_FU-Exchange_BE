package com.adkp.fuexchange;

import com.adkp.fuexchange.model.PostProduct;
import com.adkp.fuexchange.response.ResponseObject;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@OpenAPIDefinition
public class FuExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuExchangeApplication.class, args);
	}

}
