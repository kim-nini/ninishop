package org.example.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cart.Cart;
import org.example.orderservice.cart.CartJPARepository;
import org.example.orderservice.client.product.ProductClientRequest;
import org.example.orderservice.client.product.ProductClientResponse;
import org.example.orderservice.core.errors.exception.Exception400;
import org.example.orderservice.order.item.Item;
import org.example.orderservice.order.item.ItemJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.orderservice.client.product.ProductClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductClient productClient;
    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final ItemJPARepository itemJPARepository;

    // 주문하기 눌렀을 때 재고를 확인하는 메소드
    @Transactional
    public void saveOrder(String stringUserId, List<OrderRequest.OrderRequestDTO> orderRequestDTOList) {
        long userId = Long.parseLong(stringUserId);

        // 주문 하기 전에 재고 먼저 확인하면서 재고도 차감해주는 페인호출
        // 재고가 있는 경우에만 결제 완료 이후 로직을 실행해야함
        // 1개라도 재고가 부족 할 경우 실행X
        // 페인 -> 프로덕트 -> 재고가부족할경우 EXCEPTION -> saveOrder 까지 취소됨
        List<ProductClientRequest.OptionDetailForStockCheck> requestList = new ArrayList<>();
        orderRequestDTOList.stream().forEach(dto -> requestList.add(new ProductClientRequest.OptionDetailForStockCheck(dto)));

        productClient.stockCheck(requestList);

        //-----------------결제 완료 이후 로직 -------------------
        // 주문 생성
        Order order = Order.builder()
                .userId(userId)
                .status(StatusEnum.ORDER_COMPLETED)
                .build();
        orderJPARepository.save(order);


        // 주문아이템 생성 및 저장
        List<Item> items = orderRequestDTOList.stream()
                .map(dto -> Item.builder()
                        .optionId(dto.getOptionId())
                        .orderId(order.getId())
                        .quantity(dto.getQuantity())
                        .price(dto.getPrice())
                        .build())
                .collect(Collectors.toList());
        itemJPARepository.saveAll(items);

        // 장바구니 삭제
        cartJPARepository.deleteByUserId(userId);
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
}
