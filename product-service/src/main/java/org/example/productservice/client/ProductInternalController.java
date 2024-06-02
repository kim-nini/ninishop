package org.example.productservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.option.OptionRequest;
import org.example.productservice.option.OptionResponse;
import org.example.productservice.option.OptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/internal/products")
@RequiredArgsConstructor
public class ProductInternalController {
    private final OptionService optionService;

    // 장바구니 조회
    @GetMapping("/{optionId}")
    public ResponseEntity<OptionResponse.OptionProductDetailsForCart> getDetailsForCartList(@PathVariable("optionId") long optionId) {
        return ResponseEntity.ok(optionService.findByOptionId(optionId));
    }

    // 주문 요청시 재고 조회, 차감
    @PostMapping("/decrease-stocks")
    public void decreaseStockForOrderRequest(@RequestBody List<OptionRequest.OptionDetailForStockUpdate> optionDetailForStockUpdateList) {
        // body 가 void 라는 뜻이라서 header, statusLine 은 응답함 -> statusLine 값이 200이 아닐 경우를 처리하는 로직을 생성하면됨
        optionService.decreaseStockByOption(optionDetailForStockUpdateList);
    }

    /* 주문 취소시 상품 재고 복원 */
    @PostMapping("/restore-stocks")
    public void restoreStock(@RequestBody List<OptionRequest.OptionDetailForStockUpdate> optionDetailForStockUpdateList) {
        optionService.restoreStockByOption(optionDetailForStockUpdateList);
    }
}
