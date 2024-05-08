package org.example.orderservice.order;

import lombok.Getter;

@Getter
public enum StatusEnum {
    ORDER_COMPLETED(0),
    ON_DELIVERY(1),
    DELIVERY_COMPLETED(2),
    ORDER_CANCELLED(3),
    ON_RETURN(4),
    RETURN_COMPLETED(5);

    private final Integer value;

    StatusEnum(Integer value) {
        this.value = value;
    }

}
