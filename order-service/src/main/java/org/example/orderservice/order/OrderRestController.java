package org.example.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    // 주문 요청
    @PostMapping("/save")
    public ResponseEntity<ApiUtils.ApiResult<Object>> saveOrder(@RequestHeader("userId") String userId, @RequestBody List<OrderRequest.OrderRequestDTO> orderRequestDTOList) {
        orderService.saveOrder(userId, orderRequestDTOList);
        return ResponseEntity.ok(ApiUtils.success());
    }
    // @Valid : 유효성검증 -> DTO 안에 값이 들어올 때 null 이면 에러가 나게 하는거
//    public ResponseEntity<?> saveOrderV1(@RequestBody @Valid List<OrderRequest.OrderRequest> requestDTOs, Errors errors, @AuthenticationPrincipal CustomUserDetails userDetails) {
//        OrderResponse.SaveDTO responseDTO = orderService.saveOrder(requestDTOs, userDetails.getUser());
//        return ResponseEntity.ok(ApiUtils.success(responseDTO));
//    }

    /*
    * 주문조회
    * @param orderId
    * @return
    * */
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiUtils.ApiResult<OrderResponse.OrderList>> getOrder(@PathVariable("orderId") long orderId) {
        OrderResponse.OrderList response =  orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    /*
    * 주문취소
    * @param orderId
    * */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiUtils.ApiResult<Object>> cancelOrder(@PathVariable("orderId") long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiUtils.success(null));
    }




//    @PostMapping("/orders/save")

}
