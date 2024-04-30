package org.example.orderservice.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.core.errors.exception.Exception400;
import org.example.orderservice.core.errors.exception.Exception404;
import org.example.productservice.option.OptionJPARepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
//@Transactional(readOnly = true)
@Transactional
@RequiredArgsConstructor
@Service
public class CartService {

    private final CartJPARepository cartJPARepository;
//    private final OptionJPARepository optionJPARepository;

    // 장바구니 담기
//    public void addCart(List<CartRequest> request, User user){
//        for(CartRequest req : request){
//
//            // 1. 유저+옵션의 cart가 있는지 확인
//            List<Cart> cart = cartJPARepository.findByOptionIdAndUserId(req.getOptionId(), user.getId());
//
//            if(cart.isEmpty()){ // cart 없으면
//                Option option = optionJPARepository.findById(req.getOptionId()).orElseThrow(() -> new Exception404("no option : "+ req.getOptionId()));
//
//                // new cart save
//                Cart newCart = Cart.builder()
//                        .user(user)
//                        .quantity(req.getQuantity())
//                        .option(option)
//                        .price(option.getPrice() * req.getQuantity())
//                        .build();
//
//                cartJPARepository.save(newCart);
//            } else { // cart 있으면
//                // 해당 옵션의 quantity update
//                Cart oldCart = cartJPARepository.findCartByOptionIdAndUserId(req.getOptionId(), user.getId())
//                        .orElseThrow(() -> new Exception400("there is no cart for this option : "+ req.getOptionId()));
//
//                oldCart.update(req.getQuantity(), oldCart.getPrice() * req.getQuantity());
//            }
//        }
//    }
//
//    // 장바구니 보기
//    public CartResponse.FindCartDTO getCartList(User user){
//        List<Cart> cart = cartJPARepository.findByUserId(user.getId());
//        return new CartResponse.FindCartDTO(cart);
//    }


}
