package com.adkp.fuexchange.controller.campus;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.dto.CategoryDTO;
import com.adkp.fuexchange.dto.PostTypeDTO;
import com.adkp.fuexchange.service.ViewRenderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name="View Render")
public class ViewRenderController {
    private final ViewRenderService viewRenderService;
    @Autowired
    public ViewRenderController(ViewRenderService viewRenderService) {
        this.viewRenderService = viewRenderService;
    }

    @GetMapping("/campus")
    @Operation(summary = "Get all campus for rendering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fetch all Campus",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Not Available",
                    content = @Content)
    })
    public List<CampusDTO> viewAllCampus(){
        return viewRenderService.viewAllCampus();
    }

    @GetMapping("/post-type")
    @Operation(summary = "Get all post type for rendering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fetch all Post Type",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Not Available",
                    content = @Content)
    })
    public List<PostTypeDTO> viewAllPostType(){return viewRenderService.viewAllPostType();}

    @GetMapping("/category-type")
    @Operation(summary = "Get all category product type for rendering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fetch all Category Type",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Not Available",
                    content = @Content)
    })
    public List<CategoryDTO> viewAllCategoryType(){return viewRenderService.viewAllCategoryType();}


}




