package org.example.productservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.option.OptionRequest;
import org.example.productservice.option.OptionService;
import org.example.productservice.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/internal/products")
@RequiredArgsConstructor
public class ProductInternalController {
    private final ProductService productService;
    private final OptionService optionService;

    // 장바구니 조회
    @GetMapping("/{optionId}")
    public ResponseEntity<?> getDetailsForCartList(@PathVariable("optionId") long optionId) {
//        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(optionService.findByOptionId(optionId));
        return ResponseEntity.ok(optionService.findByOptionId(optionId));
    }

    // 재고 조회 및 업데이트
    @PostMapping("/stocks")
    public ResponseEntity<?> decreaseStockForOrderRequest(@RequestBody List<OptionRequest.OptionDetailForStockCheck> optionDetailForStockCheckList) {
        optionService.decreaseStockByOptionDetail(optionDetailForStockCheckList);
        return ResponseEntity.ok().build();
        // void여도 status line은 응답됨 상태값이 200이 아니면 exception으로 처리할 수 있게.. 아니면 상태값은 200인데 truefalse로 해도된다
    } // void 는 body가 void라는 뜻이라서 header, statusline은 간다

}
