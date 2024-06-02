package com.adkp.fuexchange.request;

import lombok.Data;

@Data
public class UpdateStatusRequest {

    private Integer sellerId;

    private Boolean isActive;
}
