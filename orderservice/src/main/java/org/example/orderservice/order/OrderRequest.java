package org.example.orderservice.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderRequestDTO {
        private long optionId;
        private long quantity;
        private long price;
    }
}
