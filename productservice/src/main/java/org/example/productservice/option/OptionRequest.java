package org.example.productservice.option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class OptionRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OptionDetailForStockCheck {
        private long optionId;
        private long quantity;
    }
}
