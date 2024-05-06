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
    private long orderId;
    private List<ProductDTO> productList;

    @Builder
    public OrderResponse(long orderId, ProductClientResponse.DetailForCartList productAndOptionDetail, List<Item> items) {
        this.orderId = orderId;
        this.productList = items.stream()
                .map(item -> {
                    return ProductDTO.builder()
                            .detail(productAndOptionDetail)
                            .item(item)
                            .build();
                }).collect(Collectors.toList());

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

}
