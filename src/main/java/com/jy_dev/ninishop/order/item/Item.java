package com.jy_dev.ninishop.order.item;

import com.jy_dev.ninishop.option.Option;
import com.jy_dev.ninishop.order.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="item_tb", indexes = {
        @Index(name = "item_option_id_idx", columnList = "option_id"),
        @Index(name = "item_order_id_idx", columnList = "order_id")
})
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private int price;

    private int stock;


    @Builder
    public Item(int id, Option option, Order order, int quantity, int price, int stock) {
        this.id = id;
        this.option = option;
        this.order = order;
        this.quantity = quantity;
        this.price = price;
    }
}
