package org.example.orderservice.cart;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.core.utils.ApiUtils;
import org.example.userservice.core.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/cart")
@RequiredArgsConstructor
@RestController
public class CartRestController {

    private final CartService cartService;

    // 장바구니 담기
//    @Operation(summary = "장바구니 추가 API", description = "해당 유저의 장바구니에 상품을 추가합니다. 동일한 상품과 옵션은 추가할 수 없습니다.")
//    @PostMapping("/add")
//    public ResponseEntity<?> addCart(
//            @RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
//        cartService.addCart(request,user.getUser());
//        return ResponseEntity.ok(ApiUtils.success(null));
//    }

    // (기능11) 주문하기 - (장바구니 업데이트)
////    @Operation(summary = "장바구니 상품 update API", description = "해당 유저의 장바구니 상품의 option 을 수정합니다.")
//    @PostMapping("/carts/update")
//    public ResponseEntity<?> updateCart(
//            @RequestBody List<CartRequest> request, @AuthenticationPrincipal CustomUserDetails user){
//        CartResponse.CartUpdateDTO response = cartService.updateCart(request, user.getUser());
//        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
//        return ResponseEntity.ok(apiResult);
//    }
//
    // 장바구니 보기 - (주문화면, 결재화면)
//    @Operation(summary = "장바구니 조회 API", description = "해당 유저의 장바구니 상품을 조회합니다.")
//    @GetMapping
//    public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUserDetails user){
//        CartResponse.FindCartDTO response = cartService.getCartList(user.getUser());
//        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
//        return ResponseEntity.ok(apiResult);
//    }
//
////    @Operation(summary = "장바구니 삭제 API", description = "해당 유저의 장바구니를 삭제합니다.")
//    @PostMapping("/carts/clear")
//    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUserDetails user){
//        cartService.deleteCart(user.getUser());
//        return ResponseEntity.ok(ApiUtils.success(null));
//    }

}
