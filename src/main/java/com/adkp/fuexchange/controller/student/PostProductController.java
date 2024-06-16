package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.UpdatePostProductRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post-product")
@Tag(name = "Post of Product")
@Validated
public class PostProductController {
    private final PostProductService postProductService;

    @Autowired
    public PostProductController(PostProductService postProductService) {
        this.postProductService = postProductService;
    }

    @Operation(summary = "Filter post product all case")
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

    @Operation(summary = "Get post product by postProductId")
    @GetMapping("detail/{postProductId}")
    public ResponseObject<Object> getPostProductByPostProductId(@PathVariable("postProductId") int postProductId) {
        return postProductService.getPostProductById(postProductId);
    }

    @Operation(summary = "Update post product for all information")
    @PutMapping("/update")
    public ResponseObject<Object> updatePostProduct(@RequestBody @Valid UpdatePostProductRequest updatePostProductRequest) {
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content("Cập nhật thông tin thành công!")
                .build();
    }

}
