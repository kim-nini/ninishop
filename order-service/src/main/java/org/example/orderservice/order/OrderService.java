package org.example.orderservice.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.cart.CartJPARepository;
import org.example.orderservice.client.product.ProductClientRequest;
import org.example.orderservice.client.product.ProductClientResponse;
import org.example.orderservice.core.errors.exception.Exception400;
import org.example.orderservice.order.item.Item;
import org.example.orderservice.order.item.ItemJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.orderservice.client.product.ProductClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final ProductClient productClient;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public void saveOrder(String stringUserId, List<OrderRequest.OrderRequestDTO> orderRequestDTOList) {
        long userId = Long.parseLong(stringUserId);
        // 주문 하기 전에 재고 먼저 확인하면서 재고도 차감해주는 페인호출
        // 재고가 있는 경우에만 결제 완료 이후 로직을 실행해야함
        // 1개라도 재고가 부족 할 경우 실행X
        // 페인 -> 프로덕트 -> 재고가부족할경우 EXCEPTION -> saveOrder 까지 취소됨
        List<ProductClientRequest.OptionDetailsForStockUpdate> requestList = new ArrayList<>();
        orderRequestDTOList.forEach(dto -> requestList.add(new ProductClientRequest.OptionDetailsForStockUpdate(dto)));
        productClient.decreaseStock(requestList);
        //-----------------결제 완료 이후 로직 -------------------
        Order order = Order.builder()
                .userId(userId)
                .status(StatusEnum.ORDER_COMPLETED)
                .build();
        orderJPARepository.save(order);

        List<Item> items = orderRequestDTOList.stream()
                .map(dto -> Item.builder()
                        .optionId(dto.getOptionId())
                        .orderId(order.getId())
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .build())
                .collect(Collectors.toList());
        itemJPARepository.saveAll(items);

        // 장바구니 삭제 -> 옵션 아이디 별로 삭제 하기
        orderRequestDTOList.forEach(data ->
                cartJPARepository.deleteByOptionIdAndUserId(userId, data.getOptionId()));
    }

    // 주문내역 상세조회
    @Transactional(readOnly = true)
    public OrderResponse.OrderList getOrder(long orderId) {
        List<Item> items = itemJPARepository.findAllByOrderId(orderId);

        // 주문 내역 dto
        List<OrderResponse.ProductDTO> productDTOs = items.stream()
                .map(item -> {
                    ProductClientResponse.DetailForCartList details = productClient.getDetailForCart(item.getOptionId());
                    return new OrderResponse.ProductDTO(details, item);
                }).collect(Collectors.toList());

        return OrderResponse.OrderList.builder().productList(productDTOs).orderId(orderId).build();
    }

    // 주문 상태 변경
    @Transactional
    public void updateOrderStatus() {
        // 배송상태가 ORDER_COMPLETED(0) 이고, D+1 인 orders
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Order> completedOrders = orderJPARepository.findOrdersByStatusAndOrderDate(StatusEnum.ORDER_COMPLETED,yesterday);
        log.info("completed orders: {}", completedOrders.get(0));
        completedOrders.forEach(order -> { order.updateStatus(StatusEnum.ON_DELIVERY); });

        // 배송상태가 ON_DELIVERY(1) 이고, D+2 인 orders
        LocalDate beforeYesterday = LocalDate.now().minusDays(2);
        List<Order> onDeliveryOrders = orderJPARepository.findOrdersByStatusAndOrderDate(StatusEnum.ON_DELIVERY,beforeYesterday);
        onDeliveryOrders.forEach(order -> { order.updateStatus(StatusEnum.DELIVERY_COMPLETED); });
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(long orderId) {
        Order order = orderJPARepository.findById(orderId).orElseThrow(() -> new Exception400("Order not found"));

        if(order.getStatus().equals(StatusEnum.ORDER_COMPLETED)) {
            List<Item> items = itemJPARepository.findAllByOrderId(orderId);
            List<ProductClientRequest.OptionDetailsForStockUpdate> requestList = new ArrayList<>();
            items.forEach(item -> requestList.add(new ProductClientRequest.OptionDetailsForStockUpdate(item)));
            productClient.restoreStock(requestList); // 재고 되돌리기
            // 주문 상태 변경
            order.updateStatus(StatusEnum.ORDER_CANCELLED);
        } else {
            throw new Exception400("not available cancel");
        }
    }
}
