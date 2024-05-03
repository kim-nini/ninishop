package org.example.orderservice.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.client.product.ProductClientResponse;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Getter
@Setter
@NoArgsConstructor
public class CartResponse {

    // 카트 수정
//    @Getter
//    @Setter
//    public static class CartUpdateDTO{
//        private int totalPrice;
//        private List<CartDTO> carts;
//
//        public CartUpdateDTO(List<Cart> cartList) {
//            this.carts = cartList.stream().map(CartDTO::new).collect(Collectors.toList());
//            this.totalPrice = cartList.stream().mapToInt(Cart::getPrice).sum();
//        }
//
//        @Getter
//        @Setter
//        public class CartDTO{
//            private int cartId;
//            private int optionId;
//            private String optionName;
//            private int quantity;
//            private int price;
//
//            public CartDTO(Cart cart) {
//                this.cartId = cart.getId();
//                this.optionId = cart.getOption().getId();
//                this.optionName = cart.getOption().getOptionName();
//                this.quantity = cart.getQuantity();
//                this.price = cart.getPrice();
//            }
//        }
//    }
//

    // 카트 전체 조회
    public static class FindCartDTO {
        private List<ProductDTO> products;
        private long totalPrice;

        public FindCartDTO(List<ProductDTO> products, long totalPrice) {
            this.products = products;
            this.totalPrice = totalPrice;
        }
    }

    // ProductDTO는 Product-service에서 가져온 데이터와 Cart 데이터를 결합
    @Getter
    @Setter
    public static class ProductDTO {
        private long id; // Product ID
        private String productName; // Product 이름
//        private String optionName; // 옵션 이름
        private List<CartDTO> carts;

        public ProductDTO(long productId, String productName) {
            this.id = productId;
            this.productName = productName;
        }

    }

//    @Getter
//    @Setter
//    public static class CartDTO {
//        private long id; // 카트 Id
//        private OptionDTO optionDTO;
//        private long quantity;
//        private long price;
//
//        @Builder
//        public CartDTO(long id, OptionDTO optionDTO, long quantity, long price) {
//            this.id = id;
//            this.optionDTO = optionDTO;
//            this.quantity = quantity;
//            this.price = price;
//        }
//
//    }

    @Getter
    @Setter
    public static class CartDTO {
        private Long cartId;
        private Long quantity;
        private Long price;  // Cart 내 아이템의 총 가격
        private String optionName;
        private String productName;

        public CartDTO(Cart cart, ProductClientResponse.DetailForCartList response) {
            this.cartId = cart.getId();
            this.quantity = cart.getQuantity();
            this.price = cart.getPrice();
            this.optionName = response.getOptionName();
            this.productName = response.getProductName();
        }
    }


    @Getter
    @Setter
    public static class OptionDTO {
        private long id; // 옵션아이디
        private String optionName;
        private long price; // 옵션 가격

        @Builder
        public OptionDTO(long id, String optionName, long price) {
            this.id = id;
            this.optionName = optionName;
            this.price = price;
        }
    }
}
