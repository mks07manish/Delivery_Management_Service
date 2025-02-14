package com.delivery.deliverymgmt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private String customerName;
    private String address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Date createdAt;

    public Order() {
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.createdAt = new Date(System.currentTimeMillis());
    }

    public Order(String customerName, String address) {
        this();
        this.customerName = customerName;
        this.address = address;
    }

}