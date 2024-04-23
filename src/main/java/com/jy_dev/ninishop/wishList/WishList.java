package com.jy_dev.ninishop.wishList;

import com.jy_dev.ninishop.option.Option;
import com.jy_dev.ninishop.user.User;
import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="wish_list_tb",
        indexes = {
                @Index(name = "wish_list_user_id_idx", columnList = "user_id"),
                @Index(name = "wish_list_option_id_idx", columnList = "option_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_wish_list_option_user", columnNames = {"user_id", "option_id"})
        })
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // user별로 위시리스트에 묶여 있음.

    @OneToOne(fetch = FetchType.LAZY)
    private Option option;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int price;
}
