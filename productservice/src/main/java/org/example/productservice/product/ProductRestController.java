package org.example.productservice.product;


import lombok.RequiredArgsConstructor;
import org.example.productservice.core.utils.ApiUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {
    private final ProductService productService;

    @GetMapping("/products") // 전체상품조회
    public ResponseEntity<?> getAllProducts() {
        List<ProductResponse.findAllDTO> response = productService.getAllProducts();

        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/products/{id}") // 상품 상세조회
    public ResponseEntity<?> getProductDetail(@PathVariable("id") int id) {
        ProductResponse.ProductDetailDTO response = productService.getProductDetail(id);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(response);

        return ResponseEntity.ok(apiResult);
    }

}
