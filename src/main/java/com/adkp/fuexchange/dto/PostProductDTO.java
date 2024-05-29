package com.adkp.fuexchange.dto;

import lombok.Data;

@Data
public class PostProductDTO {

    private int postProductId;

    private ProductDTO product;

    private PostTypeDTO postType;

    private CampusDTO campus;

    private PostStatusDTO postStatus;

    private int quantity;

    private String createDate;

    private String content;
}
