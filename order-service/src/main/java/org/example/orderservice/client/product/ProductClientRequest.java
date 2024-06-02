package org.example.orderservice.client.product;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.order.OrderRequest;
import org.example.orderservice.order.item.Item;

@Getter
@Setter
@NoArgsConstructor
public class ProductClientRequest {

    @Getter
    @Setter
    public static class OptionDetailsForStockUpdate {
        private long optionId;
        private long quantity;

        @Builder
        public OptionDetailsForStockUpdate(OrderRequest.OrderRequestDTO data) {
            this.optionId = data.getOptionId();
            this.quantity = data.getQuantity();
        }

        @Builder
        public OptionDetailsForStockUpdate(Item item) {
            this.optionId = item.getOptionId();
            this.quantity = item.getQuantity();
        }
    }

}
