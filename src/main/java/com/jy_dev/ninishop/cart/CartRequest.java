package com.jy_dev.ninishop.cart;

import com.jy_dev.ninishop.option.Option;
import com.jy_dev.ninishop.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class CartRequest {

    private int optionId;
    private int quantity;
    private int userId;
    private int cartId;

    public Cart toCartEntity(Option option, User user){
        return Cart.builder()
                .option(option)
                .user(user)
                .price(option.getPrice())
                .quantity(quantity)
                .build();
    }

}
