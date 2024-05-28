package com.adkp.fuexchange.controller.campus;

import com.adkp.fuexchange.dto.CampusDTO;
import com.adkp.fuexchange.service.CampusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/campus")
@Tag(name="Campus")
public class CampusController {
    private final CampusService campusService;

    @Autowired
    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @Operation(summary = "Get all campus for rendering")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Fetch all Campus",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
                    description = "Not Available",
                    content = @Content)
    })
    public List<CampusDTO> viewAllCampus(){
        return campusService.viewAllCampus();
    }
}
