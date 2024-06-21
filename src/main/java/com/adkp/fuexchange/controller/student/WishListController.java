package com.adkp.fuexchange.controller.student;

import com.adkp.fuexchange.request.RegisterWishListRequest;
import com.adkp.fuexchange.response.ResponseObject;
import com.adkp.fuexchange.response.WishListRespone;
import com.adkp.fuexchange.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@Tag(name = "wishlist")
public class WishListController {
    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }


    @Operation(summary = "Create a wishlist")
    @PostMapping("/create")
    public ResponseObject<Object>createWishList(
          @RequestBody  RegisterWishListRequest registerWishListRequest){
       return wishListService.createWishList(registerWishListRequest);
    }

    @Operation(summary = "view wishlist by postproductID")
    @GetMapping("/{postProductId}")
    public  ResponseObject<Object>viewWishListbyProduct(
            @PathVariable("postProductId") int postProductId
    ){
        return  wishListService.viewWishListByPostProductID(postProductId);
    }

    @Operation(summary = "Update quantity wishlist")
    @PostMapping("/{wishListId}/update-quantity")
    public  ResponseObject<Object>updateQuantity(
            @PathVariable("wishListId") int wishListId,
            @RequestParam("quantity") int quantity
    ){
        return  wishListService.UpdateQuantity(wishListId, quantity);
    }
    @Operation(summary = "Update status wishlist")
    @PostMapping("/{wishListId}/update-status")
    public  ResponseObject<Object>updateActive(
            @PathVariable("wishListId") int wishListId,
            @RequestParam("active") int active
    ){
        return  wishListService.UpdateActive(wishListId,active);
    }
    @Operation(summary = "Delete wishlist by wishListID")
    @DeleteMapping("/{wishListId}")
    public ResponseObject<Object>deleteWishList(
            @PathVariable("wishListId") int wishListId
    ){
        wishListService.deleteWishList(wishListId);
        return ResponseObject.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.name())
                .content(" Xóa thành công!")
                .build();
    }
}
