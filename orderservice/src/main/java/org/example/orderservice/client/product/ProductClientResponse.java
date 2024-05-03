package org.example.orderservice.client.product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductClientResponse {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DetailForCartList{
        private long productId;
        private String productName;
        private String optionName;
        private long optionPrice;
        private long optionId;

        @Builder
        public DetailForCartList(long productId, String productName, String optionName, long optionPrice, long optionId) {
            this.productId = productId;
            this.productName = productName;
            this.optionName = optionName;
            this.optionPrice = optionPrice;
            this.optionId = optionId;
        }
    }
}
