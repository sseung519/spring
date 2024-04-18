package com.example.springbasic2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private int orderPrice;

    @Column(nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Long orderId;
}
