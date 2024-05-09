package com.jy_dev.schedulerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "order-service")
public interface OrderServiceClient {


    @PostMapping("/api/orders/updateStatus")
    void updateOrderStatus();
}
