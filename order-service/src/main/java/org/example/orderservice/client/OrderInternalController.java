package org.example.orderservice.client;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.order.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/orders")
public class OrderInternalController {

    private final OrderService orderService;

    @PostMapping("/updateStatus")
    public void updateOrderStatus() {
        orderService.updateOrderStatus();
    }
}
