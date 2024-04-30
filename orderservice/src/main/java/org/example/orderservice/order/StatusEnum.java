package org.example.orderservice.order;

public enum StatusEnum {
    ORDER_COMPLETED(0,"주문완료"),
    ORDER_CANCELLED(1,"주문취소"),
    ON_DELIVERY(2,"배송중"),
    DELIVERY_OVER(3,"배송완료"),
    ON_RETURN(4,"반품신청중"),
    RETURN_OVER(5,"반품완료");
    private int status;
    private String desc;
    private StatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public int getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }
}
