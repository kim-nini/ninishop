package com.jy_dev.schedulerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service")
public interface OrderServiceClient {


    @PostMapping("/api/internal/orders/updateStatus")
    void updateOrderStatus();
}
