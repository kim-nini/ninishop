package org.example.orderservice.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.client.product.ProductClient;
import org.example.orderservice.client.product.ProductClientResponse;
import org.example.orderservice.core.errors.exception.Exception400;
import org.example.orderservice.core.errors.exception.Exception404;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
//@Transactional(readOnly = true)
@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
    private final ProductClient productClient;

    // 장바구니 담기
    public void addCart(List<CartRequest.toCartDTO> request, String userId) {
        Long longUserId = Long.parseLong(userId);

        for (CartRequest.toCartDTO req : request) {

            // 1. 유저+옵션의 cart가 있는지 확인
            List<Cart> cart = cartJPARepository.findByOptionIdAndUserId(req.getOptionId(), longUserId);

            if (cart.isEmpty()) { // cart 없으면
                // 옵션 가져오는 로직인데... 서비스통신을 해야 할 것 같은데..... option을 통해서 product도 가져와야하는데.......
                // new cart save
                Cart newCart = Cart.builder()
                        .userId(longUserId)
                        .quantity(req.getQuantity())
                        .optionId(req.getOptionId())
                        .price(req.getOptionPrice() * req.getQuantity())
                        .build();

                cartJPARepository.save(newCart);
            } else { // cart 있으면
                //해당 옵션의 quantity update
                Cart oldCart = cartJPARepository.findCartByOptionIdAndUserId(req.getOptionId(), longUserId)
                        .orElseThrow(() -> new Exception400("there is no cart for this option : " + req.getOptionId()));

                oldCart.update(req.getQuantity(), oldCart.getPrice() * req.getQuantity());
            }
        }
    }

    // 장바구니 보기
    public List<CartResponse.CartDTO> getCartList(String userId) {
        List<Cart> carts = cartJPARepository.findByUserId(Long.parseLong(userId)); // 아이디로 카트리스트받아오기

        // CART 1 -> 옵션2 -> 옵션이름
        // CART 2 -> 옵션3번 -> 옵션이름
        return carts.stream().map(cart -> {
//            OptionDetailsDTO optionDetails = productServiceClient.getOptionDetails(cart.getOptionId());
//            productClient.getDetailForCart(cart.getOptionId());
            return new CartResponse.CartDTO(cart, productClient.getDetailForCart(cart.getOptionId()));
//            return new CartResponse.CartDTO(cart, productClient.getDetailForCart(cart.getOptionId()).getResponse());
        }).collect(Collectors.toList());

//        List<CartResponse.ProductDTO> products = new ArrayList<>();
//
//        // 카트리스트를 Optionid끼리 묶어주기
//        Map<Long, List<Cart>> groupedByProduct = cartList.stream()
//                .collect(Collectors.groupingBy(Cart::getOptionId)); //
//
//        // 포문 돌리기
//        for (Map.Entry<Long, List<Cart>> entry : groupedByProduct.entrySet()) { // optionId값으로 묶어준 Cart
//
//            // 프로덕트 클라이언트로 product Name하고 productId, optionName, optionPrice 받아오기
//            ProductClientResponse.DetailForCartList response = productClient.getDetailForCart(entry.getKey()).getResponse();


            // 받아온 값으로 OptionDTO 생성
//            CartResponse.OptionDTO optionDTO =
//                    CartResponse.OptionDTO.builder()
//                            .optionName(response.getOptionName())
//                            .id(response.getOptionId())
//                            .price(response.getOptionPrice())
//                            .build();



//            CartResponse.CartDTO cartDTO = CartResponse.CartDTO.builder().optionDTO(optionDTO).id().build();
            // Products에 담아줄 CartDTO 만들어주기
//            List<CartResponse.CartDTO> cartDTOs = entry.getValue().stream()
//                    .map(cart -> new CartResponse.CartDTO(cart, optionDTO,))
//                    .collect(Collectors.toList());

//            productDTO.setCarts(cartDTOs);
//            products.add(productDTO);



        // 카트리스트에 있는 금액 다 합산
//        long totalPrice = cartList.stream().mapToLong(Cart::getPrice).sum();
//        long totalPrice = 1;
//        return new CartResponse.FindCartDTO(cartList);
//        return new CartResponse.FindCartDTO(products, totalPrice);
    }

    public void deleteOptionFromCart(String userId, String optionId) {
        long longUserId = Long.parseLong(userId);
        long longOptionId = Long.parseLong(optionId);

        cartJPARepository.deleteByOptionIdAndUserId(longUserId,longOptionId);
    }


}
