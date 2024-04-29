package com.jy_dev.ninishop.cart;

import com.jy_dev.ninishop.option.Option;
import com.jy_dev.ninishop.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CartResponse {

    // 카트 수정
    @Getter
    @Setter
    public static class CartUpdateDTO{
        private int totalPrice;
        private List<CartDTO> carts;

        public CartUpdateDTO(List<Cart> cartList) {
            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter
        @Setter
        public class CartDTO{
            private int cartId;
            private int optionId;
            private String optionName;
            private int quantity;
            private int price;

            public CartDTO(Cart cart) {
                this.cartId = cart.getId();
                this.optionId = cart.getOption().getId();
                this.optionName = cart.getOption().getOptionName();
                this.quantity = cart.getQuantity();
                this.price = cart.getPrice();
            }
        }
    }

    // 카트 보기
    @Getter
    @Setter
    public static class FindCartDTO {
        private List<ProductDTO> products;
        private int totalPrice;

        public FindCartDTO(List<Cart> cartList) {
            this.products = cartList.stream().map(cart -> cart.getOption().getProduct()).distinct()
                    .map(product -> new ProductDTO(product, cartList)).toList();
            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
        }

        @Getter
        @Setter
        public class ProductDTO{
            private int id;
            private String productName;
            private List<CartDTO> carts;

            public ProductDTO(Product product, List<Cart> carts) {
                this.id = product.getId();
                this.productName = product.getProductName();
                this.carts = carts.stream()
                        .filter(cart -> cart.getOption().getProduct().getId() == this.id) // 필터링 단계 추가
                        .map(CartDTO::new) // Cart 객체를 CartDTO로 변환
                        .toList();

            }

            @Getter
            @Setter
            public class CartDTO{
                private int id;
                private OptionDTO option;
                private int quantity;
                private int price;

                public CartDTO(Cart cart) {
                    this.id = cart.getId();
                    this.option = new OptionDTO(cart.getOption());
                    this.quantity = cart.getQuantity();
                    this.price = cart.getOption().getPrice() * cart.getQuantity();
                }

                @Getter
                @Setter
                public class OptionDTO{
                    private int id;
                    private String optionName;
                    private int price;

                    public OptionDTO(Option option) {
                        this.id = option.getId();
                        this.optionName = option.getOptionName();
                        this.price = option.getPrice();
                    }
                }
            }

        }

    }

}
