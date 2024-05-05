package org.example.orderservice.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderRestController {

    private final OrderService orderService;

    // 장바구니 전체상품 주문
    @GetMapping("/orders/save")
    public ResponseEntity<?> saveOrder(@RequestHeader("userId") String userId) {
//        List<OrderResponse> response = orderService.order(userId);
        orderService.orderTotalCartList(userId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
//    public ResponseEntity<?> saveOrderV1(@RequestBody @Valid List<OrderRequest.SaveItemDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(requestDTOs, userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }


    // (기능13) 주문 결과 확인
    //@GetMapping("/orders/{id}")


    // 사용 안함
    //@PostMapping("/orders/clear")


//    @PostMapping("/orders/save")

}
