package com.komissarov.springstore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@Table(name = "shop_order")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "order_item",
            joinColumns = @JoinColumn(name = "shop_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Collection<OrderItem> orderItems;
}
