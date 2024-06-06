package com.adkp.fuexchange.utils;

import com.adkp.fuexchange.request.OrdersRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class Utils {

    private final RestTemplate restTemplate;

    public Utils(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void navigationToSaveOrderAsync(String apiUri, OrdersRequest ordersRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<OrdersRequest> ordersRequest1 = new HttpEntity<>(ordersRequest, headers);

        String response = restTemplate
                .exchange(
                        apiUri,
                        HttpMethod.POST,
                        ordersRequest1,
                        String.class)
                .getBody();

        CompletableFuture.completedFuture(response);
    }
}
