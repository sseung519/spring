package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="order_item")
@Getter
@Setter
@ToString
public class OrderItem {
    @Id
    @Column(name="order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int orderPrice;
    private int count;

    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
