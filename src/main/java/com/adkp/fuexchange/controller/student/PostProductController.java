package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.dto.PostProductDTO;
import com.adkp.fuexchange.dto.ProductDTO;
import com.adkp.fuexchange.response.MetaPostProduct;
import com.adkp.fuexchange.response.PostProductResponse;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.PostProductService;
import com.adkp.fuexchange.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
            @RequestParam(value = "name", required = false) String name) {
        List<PostProductDTO> postProductDTO = postProductService.viewMorePostProduct(current, campusId, postTypeId, name);

        return PostProductResponse
                .builder()
                .responseObject(new ResponseObject(HttpStatusCode.valueOf(200).value(), "Success"))
                .meta(new MetaPostProduct(postProductService.countPostProduct(campusId, postTypeId, name, postProductDTO), current))
                .data(postProductDTO)
                .build();
    }
}
