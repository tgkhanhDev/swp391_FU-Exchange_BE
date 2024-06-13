package com.adkp.fuexchange.controller.student;


import com.adkp.fuexchange.request.VariationDetailRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.service.VariationDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/variation-detail")
@Tag(name = "variation Detail")
@Validated
public class VariationDetailController {
    private  final VariationDetailService variationDetailService;

    public VariationDetailController(VariationDetailService variationDetailService) {
        this.variationDetailService = variationDetailService;
    }

    @Operation(summary = "Create a variation detail")
    @PostMapping("/create-variation-detail")
    public ResponseObject<Object>createNewVariationDetail(@Valid @RequestBody VariationDetailRequest variationDetailRequest){

        return variationDetailService.CreateNewVariationByVariationID(variationDetailRequest);
    }
}
