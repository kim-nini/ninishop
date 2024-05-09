package org.example.orderservice.client.product;

import org.example.orderservice.core.utils.ApiUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductClient {


    @GetMapping("/api/internal/products/{optionId}")
    ProductClientResponse.DetailForCartList getDetailForCart(@PathVariable("optionId") long optionId);
//    ApiUtils.ApiResult<ProductClientResponse.DetailForCartList> getDetailForCart(@PathVariable("optionId") long optionId);

    @PostMapping("/api/internal/products/stocks")
    void stockCheck(@RequestBody List<ProductClientRequest.OptionDetailForStockCheck> optionDetailForStockCheckList);
}
