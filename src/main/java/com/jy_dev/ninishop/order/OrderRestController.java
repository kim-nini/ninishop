package com.jy_dev.ninishop.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderRestController {



    // (기능12) 결재
    //@PostMapping("/orders/save")


    // (기능13) 주문 결과 확인
    //@GetMapping("/orders/{id}")


    // 사용 안함
    //@PostMapping("/orders/clear")


//    @PostMapping("/orders/save")
//    public ResponseEntity<?> saveOrderV1(@RequestBody @Valid List<OrderRequest.SaveItemDTO> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(requestDTOs, userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }

}
