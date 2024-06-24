package com.adkp.fuexchange.response;

import com.adkp.fuexchange.pojo.Campus;
import com.adkp.fuexchange.pojo.PostStatus;
import com.adkp.fuexchange.pojo.PostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostProductResponse {
    private int postProductId;
    private PostType postTypeId;
    private Campus campusId;
    private PostStatus postStatusId;
    private int quantity;
    private LocalDateTime createDate;
    private String content;
    private  ProductResponse productResponse;

    private List<PostProductResponse> productResponseList;


}
