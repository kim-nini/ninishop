package org.example.productservice.option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {

    @Getter
    @Setter
    public static class OptionProductDetailsForCart{
        private long productId;
        private String productName;
        private String optionName;
        private long optionPrice;
        private long optionId;

        @Builder
        public OptionProductDetailsForCart(Option option) {
            this.optionName = option.getOptionName();
            this.optionPrice = option.getPrice();
            this.optionId = option.getId();
            this.productId = option.getProduct().getId();
            this.productName = option.getProduct().getProductName();
        }
    }
}
