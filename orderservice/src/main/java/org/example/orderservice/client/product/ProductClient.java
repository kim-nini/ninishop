package org.example.orderservice.client.product;

import org.example.orderservice.core.utils.ApiUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {

    @GetMapping("/api/internal/products/{optionId}")
    ProductClientResponse.DetailForCartList getDetailForCart(@PathVariable("optionId") long optionId);
//    ApiUtils.ApiResult<ProductClientResponse.DetailForCartList> getDetailForCart(@PathVariable("optionId") long optionId);

}
