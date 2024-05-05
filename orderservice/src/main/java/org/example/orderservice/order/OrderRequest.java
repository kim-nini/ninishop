package org.example.orderservice.order;

public class OrderRequest {

    public static class SaveItemDTO{
        private long orderListId;
        private long optionId;
        private long quantity;
        private long price;

    }
}
