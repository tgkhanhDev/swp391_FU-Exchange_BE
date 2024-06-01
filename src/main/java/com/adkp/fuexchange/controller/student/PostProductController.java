package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.response.MetaResponse;
import com.adkp.fuexchange.response.PostProductResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post-product")
@Tag(name = "Post of Product")
public class PostProductController {
    private final PostProductService postProductService;

    @Autowired
    public PostProductController(PostProductService postProductService) {
        this.postProductService = postProductService;
    }

    @GetMapping("/{current}")
    public PostProductResponse viewMorePostProduct(
            @PathVariable("current") int current,
            @RequestParam(value = "campusId", required = false) Integer campusId,
            @RequestParam(value = "postTypeId", required = false) Integer postTypeId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId
    ) {

        List<PostProductDTO> postProductDTO = postProductService.viewMorePostProduct(current, campusId, postTypeId, name, categoryId);

        return PostProductResponse
                .builder()
                .responseObject(
                        ResponseObject.builder().status(HttpStatus.OK.value())
                                .message("Success")
                                .content("Xem thêm thành công")
                                .build()
                )
                .meta(new MetaResponse(postProductService.countPostProduct(campusId, postTypeId, name, categoryId, postProductDTO), current))
                .data(postProductDTO)
                .build();
    }

    @GetMapping("detail/{postProductId}")
    public PostProductDTO getPostProductByPostId(@PathVariable("postProductId") int postProductId) {
        return postProductService.getPostProductById(postProductId);
    }

}
