package org.example.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cart.Cart;
import org.example.orderservice.cart.CartJPARepository;
import org.example.orderservice.client.product.ProductClientResponse;
import org.example.orderservice.core.errors.exception.Exception400;
import org.example.orderservice.order.item.Item;
import org.example.orderservice.order.item.ItemJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.orderservice.client.product.ProductClient;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductClient productClient;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public void orderTotalCartList(String stringUserId) {
        long userId = Long.parseLong(stringUserId);

        // userId 장바구니 가져오기
        List<Cart> carts = cartJPARepository.findByUserId(userId);
        if (carts.isEmpty()) {
            throw new Exception400("there is no carts for user : " + userId);
        }

        // 주문 생성
        Order order = Order.builder()
                .userId(userId)
                .build();
        orderJPARepository.save(order);

        // 주문아이템 생성 및 저장
        List<Item> items = carts.stream()
                .map(cart -> Item.builder()
                        .optionId(cart.getOptionId())
                        .orderId(order.getId())
                        .quantity(cart.getQuantity())
                        .price(cart.getPrice())
                        .build())
                .collect(Collectors.toList());
        itemJPARepository.saveAll(items);

        // 장바구니 삭제
        cartJPARepository.deleteByUserId(userId);

    }

//    @Transactional(readOnly = true)
//    public OrderResponse getOrder(long orderId) {
//        List<Item> items = itemJPARepository.findAllByOrderId(orderId);
//
//        // 주문 내역 dto
////        OrderResponse responses = items.stream()
////                .map(item -> {
////                    ProductClientResponse.DetailForCartList details = productClient.getDetailForCart(item.getOptionId());
////                    return new OrderResponse(orderId, details, items);
////                });
//
////        return responses;
//        return null;
//    }
}
