package org.example.orderservice.client.product;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.orderservice.order.OrderRequest;

@Getter
@Setter
@NoArgsConstructor
public class ProductClientRequest {

    @Getter
    @Setter
    public static class OptionDetailForStockCheck{
        private long optionId;
        private long quantity;

        @Builder
        public OptionDetailForStockCheck(OrderRequest.OrderRequestDTO data) {
            this.optionId = data.getOptionId();
            this.quantity = data.getQuantity();
        }
    }
}
