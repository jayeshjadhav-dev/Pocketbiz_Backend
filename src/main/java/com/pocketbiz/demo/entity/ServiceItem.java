package com.pocketbiz.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "service_items")
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    // Added this field because your Frontend uses item.category
    private String category;

    // Renamed to 'active' (Lombok generates isActive() getter, which Jackson serializes as "active")
    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    @JsonIgnore // Prevents the Infinite Loop / 500 Error
    private Vendor vendor;
}