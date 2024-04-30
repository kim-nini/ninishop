package org.example.orderservice.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.productservice.option.Option;
import org.example.userservice.user.User;


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
