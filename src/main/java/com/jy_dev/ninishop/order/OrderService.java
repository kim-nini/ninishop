package com.jy_dev.ninishop.order;

import com.jy_dev.ninishop.cart.Cart;
import com.jy_dev.ninishop.cart.CartJPARepository;
import com.jy_dev.ninishop.option.OptionJPARepository;
import com.jy_dev.ninishop.core.errors.exception.Exception400;
import com.jy_dev.ninishop.order.item.Item;
import com.jy_dev.ninishop.order.item.ItemJPARepository;
import com.jy_dev.ninishop.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderJPARepository orderJPARepository;
    private final CartJPARepository cartJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final ItemJPARepository itemJPARepository;

    @Transactional
    public OrderResponse order(User user) {
        // 일단 장바구니 가져와
        List<Cart> carts = cartJPARepository.findByUserId(user.getId());
        if(carts.isEmpty()){ //없으면 던져
            throw new Exception400("there is no carts for user : "+ user.getId());
        }

        //주문
        Order order = Order.builder()
                .user(user)
                .build();

        orderJPARepository.save(order);

        //주문내역 저장 - carts = item
        List<Item> items = new ArrayList<>();

        for(Cart cart : carts){
            Item item = Item.builder()
                    .option(cart.getOption())
                    .order(order)
                    .quantity(cart.getQuantity())
                    .price(cart.getPrice())
                    .build();

            items.add(item);
        }
        itemJPARepository.saveAll(items);

        //장바구니 지워
        cartJPARepository.deleteByUserId(user.getId());

        return new OrderResponse(order, items);

    }
}
