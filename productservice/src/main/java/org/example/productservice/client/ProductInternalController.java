package org.example.productservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.core.utils.ApiUtils;
import org.example.productservice.option.OptionService;
import org.example.productservice.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        ApiUtils.ApiResult apiResult = ApiUtils.success(optionService.findByOptionId(optionId));
        return ResponseEntity.ok(apiResult);
    }
}
