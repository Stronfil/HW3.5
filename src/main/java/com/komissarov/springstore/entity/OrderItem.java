package com.komissarov.springstore.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "shop_order_id")
    private long orderId;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private long quantity;

    public OrderItem() {
    }

    public OrderItem(long orderId, long productId, long quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
