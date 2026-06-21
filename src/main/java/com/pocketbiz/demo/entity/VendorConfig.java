package com.pocketbiz.demo.entity;

import com.pocketbiz.demo.enums.BusinessCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "vendor_configs")
public class VendorConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String themeColor;

    @Enumerated(EnumType.STRING)
    private BusinessCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private Plan plan; // Stores details like "PLATINUM"

    public VendorConfig() {}

    // --- GETTERS & SETTERS (Crucial for Controller) ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getThemeColor() { return themeColor; }
    public void setThemeColor(String themeColor) { this.themeColor = themeColor; }

    public BusinessCategory getCategory() { return category; }
    public void setCategory(BusinessCategory category) { this.category = category; }

    public Plan getPlan() { return plan; }
    public void setPlan(Plan plan) { this.plan = plan; }
}