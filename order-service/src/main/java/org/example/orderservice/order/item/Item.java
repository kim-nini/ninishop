package org.example.orderservice.order.item;

//import com.jy_dev.ninishop.option.Option;
//import com.jy_dev.ninishop.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="item_tb",
        indexes = {@Index(name = "item_option_id_idx", columnList = "option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")}
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 아이템엔 옵션아이디가 하나 들어가야함
    @Column
    private long optionId;

    @Column
    private long orderId;

    @Column(nullable = false)
    private long quantity;

    // option id별 1개 가격
    @Column(nullable = false)
    private long price;



    @Builder
    public Item(long id, long optionId, long orderId, long quantity, long price) {
        this.id = id;
        this.optionId = optionId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.price = price;
    }
}
