package com.pocketbiz.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "BASIC", "GOLD", "PLATINUM"
    private double price;
    private String duration; // e.g., "Monthly"

    public Plan() {}

    public Plan(String name, double price, String duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    // --- GETTERS & SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
}