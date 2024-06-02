package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseObject<Object> viewMorePostProduct(
            @PathVariable("current") int current,
            @RequestParam(value = "campusId", required = false) Integer campusId,
            @RequestParam(value = "postTypeId", required = false) Integer postTypeId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categoryId", required = false) Integer categoryId
    ) {
        return postProductService.viewMorePostProduct(current, campusId, postTypeId, name, categoryId);
    }

    @GetMapping("detail/{postProductId}")
    public ResponseObject<Object> getPostProductByPostId(@PathVariable("postProductId") int postProductId) {
        return postProductService.getPostProductById(postProductId);
    }

}
