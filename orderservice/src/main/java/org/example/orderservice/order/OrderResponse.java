package org.example.orderservice.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.client.product.ProductClientResponse;
import org.example.orderservice.order.item.Item;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {


    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderList{
        private long orderId;
        private List<ProductDTO> productList;
        private long totalPrice;

        @Builder
        public OrderList(long orderId, List<ProductDTO> productList) {
            this.orderId = orderId;
            this.productList = productList;
            this.totalPrice =  productList.stream()
                    .mapToLong(product -> product.getItemList().getPrice())
                    .sum();
        }
    }

    @Getter
    public static class ProductDTO {
        private String productName;
        private ItemDTO itemList;

        @Builder
        public ProductDTO(ProductClientResponse.DetailForCartList detail, Item item) {
            this.productName = detail.getProductName();
            this.itemList = ItemDTO.builder()
                    .detail(detail)
                    .item(item)
                    .build();
        }

    }

    @Getter
    public static class ItemDTO {
        private long id;
        private String optionName;
        private long quantity;
        private long price;

        @Builder
        public ItemDTO(ProductClientResponse.DetailForCartList detail, Item item) {
            this.id = item.getId();
            this.optionName = detail.getOptionName();
            this.quantity = item.getQuantity();
            this.price = detail.getOptionPrice() * item.getQuantity();
        }
    }

}
