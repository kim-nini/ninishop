package org.example.orderservice.cart;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/carts")
@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;

    // 장바구니 담기
//    @Operation(summary = "장바구니 추가 API", description = "해당 유저의 장바구니에 상품을 추가합니다. 동일한 상품과 옵션은 추가할 수 없습니다.")
    @PostMapping("/add")
    public ResponseEntity<?> addCart(
            @RequestBody List<CartRequest.toCartDTO> request, @RequestHeader("userId") String userId){

        cartService.addCart(request, userId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 장바구니 보기 - (주문화면, 결재화면)
//    @Operation(summary = "장바구니 조회 API", description = "해당 유저의 장바구니 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader("userId") String userId){
        List<CartResponse.CartDTO> response = cartService.getCartList(userId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

//    @Operation(summary = "장바구니 삭제 API", description = "해당 유저의 장바구니를 삭제합니다.")
    // 장바구니 삭제
    @PostMapping("/carts/clear")
    public ResponseEntity<?> deleteOptionFromCart(@RequestHeader("userId") String userId, @RequestBody String optionId){
        cartService.deleteOptionFromCart(userId,optionId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
//    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUserDetails user){
//        cartService.deleteCart(user.getUser());
//        return ResponseEntity.ok(ApiUtils.success(null));
//    }

}
