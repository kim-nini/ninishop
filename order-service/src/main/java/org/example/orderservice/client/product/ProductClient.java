package org.example.orderservice.client.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {

    /* 상품정보 받아오기 */
    @GetMapping("/api/internal/products/{optionId}")
    ProductClientResponse.DetailForCartList getDetailForCart(@PathVariable("optionId") long optionId);

    /* 주문 요청시 상품 재고 체크 및 차감 */
    @PostMapping("/api/internal/products/decrease-stocks")
    void decreaseStock(@RequestBody List<ProductClientRequest.OptionDetailsForStockUpdate> optionDetailForStockCheckList);

    /* 주문 취소시 상품 재고 복원 */
    @PostMapping("/api/internal/products/restore-stocks")
    void restoreStock(@RequestBody List<ProductClientRequest.OptionDetailsForStockUpdate> requestList);
}
