package com.jy_dev.schedulerservice.service;

import com.jy_dev.schedulerservice.client.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerService {

    private final OrderServiceClient orderServiceClient;

    // 매일 자정에 실행
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000)
    public void scheduleOrderStatusUpdate() {
        orderServiceClient.updateOrderStatus(); // order-service 의 order_entity_status 업데이트 api 호출
    }

}
