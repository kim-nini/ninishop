package org.example.orderservice.cart;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public class CartRequest {

    @Getter
    @Setter
    public static class toCartDTO {
        private long optionId;
        private long quantity;
//        private long userId;
        private long optionPrice;
//        private long cartId;

//        public toCartDTO(long optionId, long userId, long optionPrice, long cartId) {
//            this.optionId = optionId;
//            this.userId = userId;
//            this.optionPrice = optionPrice;
//            this.cartId = cartId;
//        }

    }
}
